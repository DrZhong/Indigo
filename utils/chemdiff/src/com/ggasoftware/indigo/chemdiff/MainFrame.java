package com.ggasoftware.indigo.chemdiff;

import com.ggasoftware.indigo.controls.CommonUtils;
import com.ggasoftware.indigo.controls.IndigoCheckedException;
import com.ggasoftware.indigo.controls.MessageBox;
import com.ggasoftware.indigo.controls.MolSaver;
import com.ggasoftware.indigo.controls.ProgressStatusDialog;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

public class MainFrame extends javax.swing.JFrame
{
   public static final int LARGE_HEIGHT = 250;
   private static final int COMPACT_HEIGHT = 70;
   private static final int MEDIUM_HEIGHT = 160;
   CompareOptions compare_options;
   CanonicalCodeGenerator csmiles_generator;
   MolSaver mol_saver1;
   MolSaver mol_saver2;

   /** Creates new form MainFrame */
   public MainFrame ()
   {
      initComponents();

      Global.indigo.setOption("render-margins", "5,2");
      Global.indigo.setOption("treat-x-as-pseudoatom", "true");
      Global.indigo.setOption("ignore-noncritical-query-features", "true");
      Global.indigo.setOption("render-coloring", true);
      Global.indigo.setOption("render-comment-font-size", "14");
      Global.indigo.setOption("render-bond-length", "70");

      allocateCompareOptions();

      setTitle("ChemDiff");

      setRowHeight(MEDIUM_HEIGHT);
   }

   private void allocateCompareOptions ()
   {
      compare_options = new CompareOptions(aromatizer_check.getState(),
              cistrans_check.getState(),
              stereocenters_check.getState(),
              unseparate_charges_check.getState());
      csmiles_generator = new CanonicalCodeGenerator(compare_options);
      out_table1.setCanonicalCodeGenerator(csmiles_generator);
      out_table2.setCanonicalCodeGenerator(csmiles_generator);
      out_table_common.setCanonicalCodeGenerator(csmiles_generator);
   }

   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      tabbed_panel = new javax.swing.JTabbedPane();
      in_tab = new javax.swing.JPanel();
      in_table_1 = new com.ggasoftware.indigo.chemdiff.InputTable();
      in_table_2 = new com.ggasoftware.indigo.chemdiff.InputTable();
      compare_button = new javax.swing.JButton();
      out_tab = new javax.swing.JPanel();
      out_table_common = new com.ggasoftware.indigo.chemdiff.OutputTable();
      out_table1 = new com.ggasoftware.indigo.chemdiff.OutputTable();
      out_table2 = new com.ggasoftware.indigo.chemdiff.OutputTable();
      main_menu_bar = new javax.swing.JMenuBar();
      menu_file = new javax.swing.JMenu();
      exit_mi = new javax.swing.JMenuItem();
      menu_view = new javax.swing.JMenu();
      jMenu1 = new javax.swing.JMenu();
      menu_view_compact = new javax.swing.JCheckBoxMenuItem();
      menu_view_medium = new javax.swing.JCheckBoxMenuItem();
      menu_view_large = new javax.swing.JCheckBoxMenuItem();
      menu_options = new javax.swing.JMenu();
      aromatizer_check = new javax.swing.JCheckBoxMenuItem();
      stereocenters_check = new javax.swing.JCheckBoxMenuItem();
      cistrans_check = new javax.swing.JCheckBoxMenuItem();
      merge_duplicates_check = new javax.swing.JCheckBoxMenuItem();
      unseparate_charges_check = new javax.swing.JCheckBoxMenuItem();
      show_duplicates_on_top_check = new javax.swing.JCheckBoxMenuItem();
      show_invalid_on_top_check = new javax.swing.JCheckBoxMenuItem();
      menu_help = new javax.swing.JMenu();
      online_help = new javax.swing.JMenuItem();
      about_mi = new javax.swing.JMenuItem();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      tabbed_panel.setPreferredSize(new java.awt.Dimension(811, 910));

      in_tab.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
      in_tab.setPreferredSize(new java.awt.Dimension(660, 760));
      in_tab.setLayout(new java.awt.GridBagLayout());

      in_table_1.setTitle("First file");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      in_tab.add(in_table_1, gridBagConstraints);

      in_table_2.setTitle("Second file");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      in_tab.add(in_table_2, gridBagConstraints);

      compare_button.setText("Compare");
      compare_button.setMargin(new java.awt.Insets(5, 30, 5, 30));
      compare_button.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            compare_buttonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
      in_tab.add(compare_button, gridBagConstraints);

      tabbed_panel.addTab("input", in_tab);

      out_tab.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
      out_tab.setLayout(new java.awt.GridBagLayout());

      out_table_common.setMinimumSize(new java.awt.Dimension(175, 179));
      out_table_common.setTitle("Common");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      out_tab.add(out_table_common, gridBagConstraints);

      out_table1.setIdColumnCount(1);
      out_table1.setMinimumSize(new java.awt.Dimension(135, 179));
      out_table1.setTitle("Unique in 1st");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      out_tab.add(out_table1, gridBagConstraints);

      out_table2.setIdColumnCount(1);
      out_table2.setMinimumSize(new java.awt.Dimension(135, 179));
      out_table2.setTitle("Unique in 2nd");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      out_tab.add(out_table2, gridBagConstraints);

      tabbed_panel.addTab("output", out_tab);

      menu_file.setText("File");
      menu_file.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            load_secondActionPerformed(evt);
         }
      });

      exit_mi.setText("Exit");
      exit_mi.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            exit_miActionPerformed(evt);
         }
      });
      menu_file.add(exit_mi);

      main_menu_bar.add(menu_file);

      menu_view.setText("View");

      jMenu1.setText("Layout size");

      menu_view_compact.setText("Compact");
      menu_view_compact.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            onLayoutSizeChanged(evt);
         }
      });
      jMenu1.add(menu_view_compact);

      menu_view_medium.setSelected(true);
      menu_view_medium.setText("Medium");
      menu_view_medium.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            onLayoutSizeChanged(evt);
         }
      });
      jMenu1.add(menu_view_medium);

      menu_view_large.setText("Large");
      menu_view_large.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            onLayoutSizeChanged(evt);
         }
      });
      jMenu1.add(menu_view_large);

      menu_view.add(jMenu1);

      main_menu_bar.add(menu_view);

      menu_options.setText("Options");

      aromatizer_check.setSelected(true);
      aromatizer_check.setText("Aromatize molecules");
      aromatizer_check.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            aromatizer_checkStateChanged(evt);
         }
      });
      menu_options.add(aromatizer_check);

      stereocenters_check.setText("Ignore stereocenters");
      stereocenters_check.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            stereocenters_checkStateChanged(evt);
         }
      });
      menu_options.add(stereocenters_check);

      cistrans_check.setText("Ignore cis-trans bonds");
      cistrans_check.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            cistrans_checkStateChanged(evt);
         }
      });
      menu_options.add(cistrans_check);

      merge_duplicates_check.setSelected(true);
      merge_duplicates_check.setText("Merge duplicate molecules");
      merge_duplicates_check.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
      menu_options.add(merge_duplicates_check);

      unseparate_charges_check.setSelected(true);
      unseparate_charges_check.setText("Dipole to covalent bond");
      unseparate_charges_check.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            unseparate_charges_checkStateChanged(evt);
         }
      });
      menu_options.add(unseparate_charges_check);

      show_duplicates_on_top_check.setSelected(true);
      show_duplicates_on_top_check.setText("Show duplicates on top");
      menu_options.add(show_duplicates_on_top_check);

      show_invalid_on_top_check.setSelected(true);
      show_invalid_on_top_check.setText("Show invalid molecules on top");
      menu_options.add(show_invalid_on_top_check);

      main_menu_bar.add(menu_options);

      menu_help.setText("Help");

      online_help.setText("Online Help");
      online_help.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            online_helpActionPerformed(evt);
         }
      });
      menu_help.add(online_help);

      about_mi.setText("About");
      about_mi.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            about_miActionPerformed(evt);
         }
      });
      menu_help.add(about_mi);

      main_menu_bar.add(menu_help);

      setJMenuBar(main_menu_bar);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(tabbed_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(tabbed_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

    private void load_secondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_load_secondActionPerformed
       in_table_2.openLoadingDialog();
    }//GEN-LAST:event_load_secondActionPerformed

    private void exit_miActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_miActionPerformed
       dispose();
    }//GEN-LAST:event_exit_miActionPerformed

    private void about_miActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_about_miActionPerformed
       CommonUtils.showAboutDialog(this, "ChemDiff", "http://ggasoftware.com/opensource/indigo/chemdiff");
    }//GEN-LAST:event_about_miActionPerformed

    private void compare_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compare_buttonActionPerformed

       final ProgressStatusDialog dlg = new ProgressStatusDialog(this, true);

       // Update molecules properties
       SwingWorker<Void, Void> compare_molecules = new SwingWorker<Void, Void>()
       {
          private int max_steps = 3;
          private int cur_step = 0;
          private int mols_error_count = 0;

          private void setStepProgress (int progress)
          {
             setProgress((100 * cur_step + progress) / max_steps);
          }

          private HashMap<String, MultipleMoleculeItem> getCanonicalMap (ArrayList<MoleculeItem> mols)
          {
             HashMap<String, MultipleMoleculeItem> map = new HashMap<String, MultipleMoleculeItem>();
             HashMap<String, Integer> same_canonical_size = new HashMap<String, Integer>();

             boolean merge = merge_duplicates_check.getState();

             int processed = 0;
             for (MoleculeItem m : mols)
             {
                setStepProgress(100 * processed / mols.size());
                processed++;
                
                if ((processed % 10000) == 0)
                {
                   System.gc();
                   System.out.println(String.format("Indigo objects count after %d molecules: %d", 
                           processed, Global.indigo.countReferences()));
                }
                if (isCancelled())
                   return null;
            
                String can_smiles, src_can_smiles;
                String error_msg = null;
                try
                {
                   src_can_smiles = csmiles_generator.generate(m);
                }
                catch (IndigoCheckedException ex)
                {
                   error_msg = ex.getMessage();
                   // Generate unique error message and save it as a smiles
                   src_can_smiles = String.format("Error #%d: %s", mols_error_count, error_msg);
                   mols_error_count++;
                }
                can_smiles = src_can_smiles;
                // Check if this smiles already presents in the map
                if (map.containsKey(can_smiles))
                {
                   Integer count = same_canonical_size.get(can_smiles);
                   count++;

                   if (merge)
                   {
                      // Add this molecules to the existing group
                      map.get(can_smiles).getGroup(0).add(m);
                      continue;
                   }
                   else
                   {
                      // Add serial number to the canonical smiles
                      can_smiles = can_smiles + " $" + count.toString();
                   }
                }

                MultipleMoleculeItem mul_item = new MultipleMoleculeItem(m, csmiles_generator);
                mul_item.setErrorMessageToRender(error_msg);
                mul_item.setCanonicalCode(src_can_smiles);
                map.put(can_smiles, mul_item);
                same_canonical_size.put(can_smiles, 1);
             }
             System.out.println(String.format("Indigo objects count: %d", Global.indigo.countReferences()));
             return map;
          }

          @Override
          protected Void doInBackground () throws Exception
          {
             ArrayList<MoleculeItem> set1 = in_table_1.getMolecules();
             ArrayList<MoleculeItem> set2 = in_table_2.getMolecules();

             // Create canonical code mappings
             cur_step = 0;
             dlg.setStepName("Preparing the first set");
             HashMap<String, MultipleMoleculeItem> map1 = getCanonicalMap(set1);

             cur_step = 1;
             dlg.setStepName("Preparing the second set");
             HashMap<String, MultipleMoleculeItem> map2 = getCanonicalMap(set2);

             if (isCancelled())
                return null;
             
             setProgress(10);
             // Intersect and find difference
             Set<String> keys1 = map1.keySet();
             Set<String> keys2 = map2.keySet();

             Set<String> unique1_keys = new HashSet<String>(keys1);
             unique1_keys.removeAll(keys2);

             Set<String> unique2_keys = new HashSet<String>(keys2);
             unique2_keys.removeAll(keys1);

             Set<String> common_keys = new HashSet<String>(keys1);
             common_keys.removeAll(unique1_keys);

             setProgress(100);

             ArrayList<MultipleMoleculeItem> common_mols = new ArrayList<MultipleMoleculeItem>();
             ArrayList<MultipleMoleculeItem> unique1_mols = new ArrayList<MultipleMoleculeItem>();
             ArrayList<MultipleMoleculeItem> unique2_mols = new ArrayList<MultipleMoleculeItem>();

             // Create list with common molecules
             for (String common_key : common_keys)
             {
                MultipleMoleculeItem common_mol = new MultipleMoleculeItem(2, csmiles_generator);
                // Add molecules from the first group
                common_mol.getGroup(0).addAll(map1.get(common_key).getGroup(0));
                // Add molecules from the second group
                common_mol.getGroup(1).addAll(map2.get(common_key).getGroup(0));
                common_mol.setCanonicalCode(map1.get(common_key).getCanonicalCode());
                common_mols.add(common_mol);
             }

             // Create lists with unique molecules
             for (String key1 : unique1_keys)
                unique1_mols.add(map1.get(key1));
             for (String key2 : unique2_keys)
                unique2_mols.add(map2.get(key2));

             final boolean sort_by_size = show_duplicates_on_top_check.getState();
             final boolean show_invalid_on_top = show_invalid_on_top_check.getState();
             
             Comparator<MultipleMoleculeItem> comparator = new Comparator<MultipleMoleculeItem>()
             {
                public int compare (MultipleMoleculeItem o1, MultipleMoleculeItem o2)
                {
                   if (show_invalid_on_top)
                   {
                      Boolean b1 = (o1.getErrorMessageToRender() != null);
                      Boolean b2 = (o2.getErrorMessageToRender() != null);
                      if (b1 != b2)
                         return b2.compareTo(b1);
                   }
                   if (sort_by_size)
                   {
                      int s1 = o1.getGroup(0).size();
                      if (o1.getGroupCount() > 1)
                         s1 += o1.getGroup(1).size();
                      int s2 = o2.getGroup(0).size();
                      if (o2.getGroupCount() > 1)
                         s2 += o2.getGroup(1).size();
                      if (s1 != s2)
                         return s2 - s1;
                   }
                   String id1 = o1.getId(0), id2 = o2.getId(0);
                   Integer len1 = id1.length(), len2 = id2.length();
                   if (len1 != len2)
                      return len1.compareTo(len2);
                   return id1.compareTo(id2);
                }
             };
             
             if (isCancelled())
                return null;
             
             Collections.sort(unique1_mols, comparator);
             Collections.sort(unique2_mols, comparator);
             Collections.sort(common_mols, comparator);

             cur_step = 2;
             dlg.setStepName("Comparing the molecules");
             setStepProgress(25);
             out_table1.setMolecules(unique1_mols);
             setStepProgress(50);
             out_table2.setMolecules(unique2_mols);
             setStepProgress(75);
             out_table_common.setMolecules(common_mols);
             setStepProgress(100);

             if (isCancelled())
                return null;
             
             tabbed_panel.setSelectedIndex(1);
             allocateCompareOptions();
             return null;
          }
       };

       dlg.setTitle("Comparing the molecules...");
       dlg.executeSwingWorker(compare_molecules);
       boolean cancelled = false;
       try
       {
          // Check if work wasn't aborted
          compare_molecules.get();
       }
       catch (InterruptedException ex)
       {
          cancelled = true;
       }
       catch (ExecutionException ex)
       {
          StringWriter sw = new StringWriter();
          ex.printStackTrace(new PrintWriter(sw));
          String error_as_string = sw.toString();

          MessageBox.show(null, error_as_string, "Error", MessageBox.ICON_ERROR);
          
          cancelled = true;
       }
       if (cancelled)
       {
          out_table1.clear();
          out_table2.clear();
          out_table_common.clear();
       }
}//GEN-LAST:event_compare_buttonActionPerformed

    private void aromatizer_checkStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_aromatizer_checkStateChanged
       compare_options.setAromFlag(aromatizer_check.getState());
    }//GEN-LAST:event_aromatizer_checkStateChanged

    private void stereocenters_checkStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stereocenters_checkStateChanged
       compare_options.setStereocentersIgnoreFlag(stereocenters_check.getState());
    }//GEN-LAST:event_stereocenters_checkStateChanged

    private void cistrans_checkStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cistrans_checkStateChanged
       compare_options.setCisTransIgnoreFlag(cistrans_check.getState());
    }//GEN-LAST:event_cistrans_checkStateChanged

    private void unseparate_charges_checkStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_unseparate_charges_checkStateChanged
       compare_options.setUnseparateChargesFlag(unseparate_charges_check.getState());
    }//GEN-LAST:event_unseparate_charges_checkStateChanged

    private void online_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_online_helpActionPerformed
       try
       {
          java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
          java.net.URI uri = new java.net.URI("http://ggasoftware.com/opensource/indigo/chemdiff");
          desktop.browse(uri);
       }
       catch (URISyntaxException ex)
       {
          Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
       }
       catch (IOException ex)
       {
          Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
       }

    }//GEN-LAST:event_online_helpActionPerformed

    private void onLayoutSizeChanged(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onLayoutSizeChanged
    {//GEN-HEADEREND:event_onLayoutSizeChanged
       menu_view_compact.setState(false);
       menu_view_medium.setState(false);
       menu_view_large.setState(false);
       if (evt.getSource() == menu_view_compact)
       {
          setRowHeight(COMPACT_HEIGHT);
          menu_view_compact.setState(true);
       }
       else if (evt.getSource() == menu_view_medium)
       {
          setRowHeight(MEDIUM_HEIGHT);
          menu_view_medium.setState(true);
       }
       else
       {
          setRowHeight(LARGE_HEIGHT);
          menu_view_large.setState(true);
       }
    }//GEN-LAST:event_onLayoutSizeChanged

   private void setRowHeight (int height)
   {
      in_table_1.setRowHeight(height);
      in_table_2.setRowHeight(height);
      out_table_common.setRowHeight(height);
      out_table1.setRowHeight(height);
      out_table2.setRowHeight(height);
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JMenuItem about_mi;
   private javax.swing.JCheckBoxMenuItem aromatizer_check;
   private javax.swing.JCheckBoxMenuItem cistrans_check;
   private javax.swing.JButton compare_button;
   private javax.swing.JMenuItem exit_mi;
   private javax.swing.JPanel in_tab;
   private com.ggasoftware.indigo.chemdiff.InputTable in_table_1;
   private com.ggasoftware.indigo.chemdiff.InputTable in_table_2;
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenuBar main_menu_bar;
   private javax.swing.JMenu menu_file;
   private javax.swing.JMenu menu_help;
   private javax.swing.JMenu menu_options;
   private javax.swing.JMenu menu_view;
   private javax.swing.JCheckBoxMenuItem menu_view_compact;
   private javax.swing.JCheckBoxMenuItem menu_view_large;
   private javax.swing.JCheckBoxMenuItem menu_view_medium;
   private javax.swing.JCheckBoxMenuItem merge_duplicates_check;
   private javax.swing.JMenuItem online_help;
   private javax.swing.JPanel out_tab;
   private com.ggasoftware.indigo.chemdiff.OutputTable out_table1;
   private com.ggasoftware.indigo.chemdiff.OutputTable out_table2;
   private com.ggasoftware.indigo.chemdiff.OutputTable out_table_common;
   private javax.swing.JCheckBoxMenuItem show_duplicates_on_top_check;
   private javax.swing.JCheckBoxMenuItem show_invalid_on_top_check;
   private javax.swing.JCheckBoxMenuItem stereocenters_check;
   private javax.swing.JTabbedPane tabbed_panel;
   private javax.swing.JCheckBoxMenuItem unseparate_charges_check;
   // End of variables declaration//GEN-END:variables
}
