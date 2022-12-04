/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class FereastraComanda extends javax.swing.JFrame {

    /**
     * Creates new form FereastraComanda
     */
    public FereastraComanda() {
        initComponents();
        Produs.citeste();
        Produs.completeazaJtable((DefaultTableModel) jTableProduse.getModel());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableComanda = new javax.swing.JTable();
        butonPlata = new javax.swing.JButton();
        butonAdaugareProdus = new javax.swing.JButton();
        butonAnulare = new javax.swing.JButton();
        campTotal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProduse = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableComanda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nume", "pret"
            }
        ));
        jScrollPane1.setViewportView(jTableComanda);

        butonPlata.setText("plata cash");
        butonPlata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonPlataActionPerformed(evt);
            }
        });

        butonAdaugareProdus.setText("adauga produs");
        butonAdaugareProdus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugareProdusActionPerformed(evt);
            }
        });

        butonAnulare.setText("anuleaza comanda");
        butonAnulare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAnulareActionPerformed(evt);
            }
        });

        campTotal.setText("total");

        jTableProduse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nume", "pret"
            }
        ));
        jScrollPane2.setViewportView(jTableProduse);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butonAdaugareProdus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butonAnulare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butonPlata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 61, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butonAdaugareProdus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butonAnulare)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butonPlata)
                            .addComponent(campTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butonPlataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonPlataActionPerformed
        // TODO add your handling code here:
        int idUser = FereastraLogin.idUser;
        int idMasa = getIdMasa();
        int total = Comanda.calculeazaTotal(getIndexComanda());
        Nota nota = new Nota(idUser, idMasa, total);
        BazaDeDate.adaugaNota(nota);
        butonAnulare.doClick();
    }//GEN-LAST:event_butonPlataActionPerformed

    private void butonAdaugareProdusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugareProdusActionPerformed
        // TODO add your handling code here:

        // Verifica produs selectat din jTable produse
        if (jTableProduse.getSelectedRowCount() != 1) {
            return;
        }
        
        int indexRand = jTableProduse.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTableProduse.getModel();
        String idProdus = (String) model.getValueAt(indexRand, 0);
        int indexProdus = Produs.getIndex(Integer.parseInt(idProdus));
        Produs produs = Produs.get(indexProdus);
        
        // Adauga in comanda
        int indexComanda = getIndexComanda();
        Comanda.adaugaProdus(indexComanda, produs);
        
        // Adauga in jTable comanda
        model = (DefaultTableModel) jTableComanda.getModel();
        Comanda.adaugaJtable(model, produs);
        
        // Actualizeaza total
        int total = Comanda.calculeazaTotal(indexComanda);
        campTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_butonAdaugareProdusActionPerformed

    private void butonAnulareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAnulareActionPerformed
        // TODO add your handling code here:
        
        // Actualizeaza lista produse din comanda
        int indexComanda = getIndexComanda();
        Comanda.get(indexComanda).reseteaza();
        
        // Actualizare JTable
        DefaultTableModel model = (DefaultTableModel) jTableComanda.getModel();
        int nrRanduri = jTableComanda.getRowCount();
        for (int i = nrRanduri-1; i >= 0; i--) {
            model.removeRow(i);
        }
        campTotal.setText("0");
    }//GEN-LAST:event_butonAnulareActionPerformed
    
    public int getIdMasa() {
        String stringIdMasa = this.getTitle();
        return Integer.parseInt(stringIdMasa);
    }
    
    public int getIndexComanda() {
        int idMasa = getIdMasa();
        return Comanda.getIndex(idMasa);
    }

    public DefaultTableModel getModelJtableComanda() {
        return (DefaultTableModel) jTableComanda.getModel();
    }

    public javax.swing.JTextField getJtextFieldTotal() {
        return campTotal;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FereastraComanda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FereastraComanda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FereastraComanda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FereastraComanda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FereastraComanda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butonAdaugareProdus;
    private javax.swing.JButton butonAnulare;
    private javax.swing.JButton butonPlata;
    private javax.swing.JTextField campTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableComanda;
    private javax.swing.JTable jTableProduse;
    // End of variables declaration//GEN-END:variables
}
