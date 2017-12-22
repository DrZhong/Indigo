/****************************************************************************
 * Copyright (C) 2009-2015 EPAM Systems
 *
 * This file is part of Indigo toolkit.
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation and appearing in the file LICENSE.GPL included in the
 * packaging of this file.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 ***************************************************************************/

#include <molecule/elements.h>
#include <cmath>
#include <vector>
#include <set>
#include <algorithm>
#include "molecule/molecule_morgan_fingerprint_builder.h"

using namespace indigo;


MoleculeMorganFingerprintBuilder::MoleculeMorganFingerprintBuilder(BaseMolecule &mol) : mol(mol) {}

void MoleculeMorganFingerprintBuilder::calculateDescriptorsECFP(int fp_depth, Array<dword> &res) {
   initDescriptors(initialStateCallback_ECFP);
   buildDescriptors(fp_depth);
   
   res.clear();
   
   for(auto& feature : features) {
      res.push(feature.hash);
   }
}

void MoleculeMorganFingerprintBuilder::calculateDescriptorsFCFP(int fp_depth, Array<dword> &res) {
   initDescriptors(initialStateCallback_FCFP);
   buildDescriptors(fp_depth);

   res.clear();

   for(auto& feature : features) {
      res.push(feature.hash);
   }
}

void MoleculeMorganFingerprintBuilder::writeFingerprintECFP(int fp_depth, byte *fp, int size) {
   initDescriptors(initialStateCallback_ECFP);
   buildDescriptors(fp_depth);

   for(auto& feature : features) {
      setBits(feature.hash, fp, size);
   }
}

void MoleculeMorganFingerprintBuilder::writeFingerprintFCFP(int fp_depth, byte *fp, int size) {
   initDescriptors(initialStateCallback_FCFP);
   buildDescriptors(fp_depth);

   for(auto& feature : features) {
      setBits(feature.hash, fp, size);
   }
}

void MoleculeMorganFingerprintBuilder::setBits(dword hash, byte *fp, int size)
{
   unsigned seed = hash;

   // fill random bits
   seed = seed * 0x8088405 + 1;

   // Uniformly distributed bits
   unsigned n = (unsigned)(((qword)(size * 8) * seed) / (unsigned)(-1));

   unsigned nByte = n / 8;
   unsigned nBit = n - nByte * 8;

   fp[nByte] = fp[nByte] | (byte)(1 << nBit);
}

void MoleculeMorganFingerprintBuilder::initDescriptors(InitialStateCallback initialStateCallback) {
   features.clear();
   atom_descriptors.clear();

   for(int idx : mol.vertices()) {
      AtomDescriptor atom_descriptor;
      atom_descriptor.descr.hash = initialStateCallback(mol, idx);

      const Vertex &vertex = mol.getVertex(idx);

      for(auto& nei : vertex.neighbors()) {
         int edge_idx = vertex.neiEdge(nei);
         int vertex_idx = vertex.neiVertex(nei);

         int bond_type = mol.getBondOrder(edge_idx);

         atom_descriptor.bond_descriptors.push_back(BondDescriptor {bond_type, vertex_idx, edge_idx});
      }

      atom_descriptors.push_back(atom_descriptor);
   }
}

void MoleculeMorganFingerprintBuilder::buildDescriptors(int fp_depth) {
   for(int i = 0; i < fp_depth; i++) {
      calculateNewAtomDescriptors(i);

      // Update all atom descriptors simultaneously
      std::vector<FeatureDescriptor> new_features;
      for (auto &atom : atom_descriptors) {
         atom.descr = atom.new_descr;

         const auto &duplicate = std::find(new_features.begin(), new_features.end(), atom.descr);
         if(duplicate == new_features.end()) {
            new_features.push_back(atom.descr);
         } else if(atom.descr.hash < duplicate->hash) { // the leaser hash is preferred
            new_features.erase(duplicate);
            new_features.push_back(atom.descr);
         }
      }

      // Features are sorted by their iteration number, then by their hash
      std::sort(new_features.begin(), new_features.end(),
                [](const FeatureDescriptor & fd1, const FeatureDescriptor & fd2) {
                   return fd1.hash < fd2.hash;
                });

      // Update features
      for(auto& feature : new_features) {
         if(std::find(features.begin(), features.end(), feature) == features.end()) {
            features.push_back(feature);  // add unique
         }
      }
   }
}

void MoleculeMorganFingerprintBuilder::calculateNewAtomDescriptors(int iterationNumber) {
   for (auto &atom : atom_descriptors) {
      std::sort(atom.bond_descriptors.begin(), atom.bond_descriptors.end(),
                [](const BondDescriptor &bd1, const BondDescriptor &bd2) {
                   return bondDescriptorCmp(bd1, bd2) < 0;
                });

      atom.new_descr.hash = (dword) iterationNumber * 37 + atom.descr.hash;
      atom.new_descr.bond_set.clear();

      for (auto &bond : atom.bond_descriptors) {
         FeatureDescriptor &descr = atom_descriptors[bond.vertex_idx].descr;

         atom.new_descr.hash = 37 * atom.new_descr.hash + bond.bond_type;
         atom.new_descr.hash = 37 * atom.new_descr.hash + descr.hash;

         atom.new_descr.bond_set.insert(bond.edge_idx);
         atom.new_descr.bond_set.insert(descr.bond_set.begin(), descr.bond_set.end());
      }
   }
}

dword MoleculeMorganFingerprintBuilder::initialStateCallback_ECFP(BaseMolecule &mol, int idx) {
   int nonhydrogen_neighbors = 0;
   for(auto& nei : mol.getVertex(idx).neighbors()) {
      if (mol.getAtomNumber(nei) != ELEM_H)
         nonhydrogen_neighbors += 1;
   }

   double atomic_weight = Element::getStandardAtomicWeight(mol.getAtomNumber(idx));

   dword key = 1;
   key = key * 37 + nonhydrogen_neighbors;
   key = key * 37 + mol.getAtomValence(idx) - mol.getAtomTotalH(idx);
   key = key * 37 + mol.getAtomNumber(idx);
   key = key * 37 + (int) std::round(atomic_weight);
   key = key * 37 + mol.getAtomCharge(idx);
   key = key * 37 + mol.getAtomTotalH(idx);
   key = key * 37 + mol.vertexInRing(idx);

   return key;
}

// TODO - some atom characteristics need implementing
dword MoleculeMorganFingerprintBuilder::initialStateCallback_FCFP(BaseMolecule &mol, int idx) {
   throw Exception("FCFP is not implemented");

   mol.getVertex(0).neiEdge(0);
   mol.getEdge(0);
   mol.getAtomAromaticity(0) == ATOM_AROMATIC;
   Element::isHalogen(mol.getAtomNumber(idx));

   dword key = 0;

   key |= (mol.getAtomAromaticity(0) == ATOM_AROMATIC)   << 4;
   key |= Element::isHalogen(mol.getAtomNumber(idx))     << 5;

   return key;
}

int MoleculeMorganFingerprintBuilder::bondDescriptorCmp(const BondDescriptor &bd1, const BondDescriptor &bd2) {
   if(bd1.bond_type != bd2.bond_type)
      return bd1.bond_type - bd2.bond_type;

   AtomDescriptor &ad1 = atom_descriptors[bd1.vertex_idx];
   AtomDescriptor &ad2 = atom_descriptors[bd2.vertex_idx];

   return ad1.descr.hash - ad2.descr.hash;
}

bool MoleculeMorganFingerprintBuilder::FeatureDescriptor::operator==(const FeatureDescriptor &rhs) const {
   return bond_set == rhs.bond_set;
}

bool MoleculeMorganFingerprintBuilder::FeatureDescriptor::operator<(
      const MoleculeMorganFingerprintBuilder::FeatureDescriptor &rhs) const {
   return bond_set < rhs.bond_set;
}