/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.convertPoint;

/**
 *
 * @author User
 */
public class FereastraMeseAdmin extends javax.swing.JFrame
                                implements MouseListener, MouseMotionListener {

    /**
     * Creates new form FereastraMeseAdmin
     */
    private Point pozitieStart;
    private JButton masaSelectata;
    private Boolean modStergere;

    public FereastraMeseAdmin() {
        initComponents();
        
        // Initializare variabile
        modStergere = false;
        
        BazaDeDate.extrageMese(panouMese, this);
        
        panouMese.revalidate();
        panouMese.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butonAdaugareMasa = new javax.swing.JButton();
        panouMese = new javax.swing.JPanel();
        butonModStergere = new javax.swing.JButton();
        butonGestionareAngajati = new javax.swing.JButton();
        butonGestionareProduse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        butonAdaugareMasa.setText("adauga masa");
        butonAdaugareMasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugareMasaActionPerformed(evt);
            }
        });

        panouMese.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout panouMeseLayout = new javax.swing.GroupLayout(panouMese);
        panouMese.setLayout(panouMeseLayout);
        panouMeseLayout.setHorizontalGroup(
            panouMeseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );
        panouMeseLayout.setVerticalGroup(
            panouMeseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 339, Short.MAX_VALUE)
        );

        butonModStergere.setText("mod stergere");
        butonModStergere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonModStergereActionPerformed(evt);
            }
        });

        butonGestionareAngajati.setText("gestioneaza angajati");
        butonGestionareAngajati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonGestionareAngajatiActionPerformed(evt);
            }
        });

        butonGestionareProduse.setText("gestioneaza produse");
        butonGestionareProduse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonGestionareProduseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(panouMese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(butonAdaugareMasa)
                            .addComponent(butonModStergere))
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(butonGestionareProduse)
                            .addComponent(butonGestionareAngajati))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(butonAdaugareMasa)
                        .addGap(18, 18, 18)
                        .addComponent(butonModStergere)
                        .addGap(34, 34, 34)
                        .addComponent(butonGestionareAngajati)
                        .addGap(18, 18, 18)
                        .addComponent(butonGestionareProduse))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(panouMese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butonAdaugareMasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugareMasaActionPerformed
        // TODO add your handling code here:
        int xImplicit = 100;
        int yImplicit = 100;
        Masa masa = new Masa(xImplicit, yImplicit, panouMese);
        masa.buton.addMouseListener(this);
        masa.buton.addMouseMotionListener(this);

        if (Masa.verifica(masa.buton)) {
            Masa.sterge(masa.buton, panouMese);
            return;
        }
        
        BazaDeDate.adaugaMasa(xImplicit, yImplicit);
        
        panouMese.revalidate();
        panouMese.repaint();
    }//GEN-LAST:event_butonAdaugareMasaActionPerformed

    private void butonModStergereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonModStergereActionPerformed
        // TODO add your handling code here:
        if (modStergere == false) {
            butonModStergere.setBackground(Color.RED);
            modStergere = true;
        } else {
            modStergere = false;
            butonModStergere.setBackground(butonAdaugareMasa.getBackground());
        }
    }//GEN-LAST:event_butonModStergereActionPerformed

    private void butonGestionareAngajatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonGestionareAngajatiActionPerformed
        // TODO add your handling code here:
        //this.setVisible(false);
        FereastraAngajati f = new FereastraAngajati();
        f.setVisible(true);
    }//GEN-LAST:event_butonGestionareAngajatiActionPerformed

    private void butonGestionareProduseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonGestionareProduseActionPerformed
        // TODO add your handling code here:
        //this.setVisible(false);
        FereastraProduse f = new FereastraProduse();
        f.setVisible(true);
    }//GEN-LAST:event_butonGestionareProduseActionPerformed

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
            java.util.logging.Logger.getLogger(FereastraMeseAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FereastraMeseAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FereastraMeseAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FereastraMeseAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FereastraMeseAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butonAdaugareMasa;
    private javax.swing.JButton butonGestionareAngajati;
    private javax.swing.JButton butonGestionareProduse;
    private javax.swing.JButton butonModStergere;
    private javax.swing.JPanel panouMese;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Detectarea mesei apasate
        masaSelectata = (JButton) e.getSource();
        if (modStergere == true) {
            BazaDeDate.stergeMasa(masaSelectata);
            Masa.sterge(masaSelectata, panouMese);
            panouMese.revalidate();
            panouMese.repaint();
            return;
        }
        pozitieStart = SwingUtilities.convertPoint(masaSelectata, e.getPoint(),
                                                    masaSelectata.getParent());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pozitieStart = null;
        masaSelectata = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pozitieMouse = convertPoint(masaSelectata, e.getPoint(),
                                            masaSelectata.getParent());
        
        if (this.getBounds().contains(pozitieMouse)) {
            Point pozitieVeche = masaSelectata.getLocation();
            Point pozitieNoua = masaSelectata.getLocation();
            pozitieNoua.translate(pozitieMouse.x - pozitieStart.x,
                                    pozitieMouse.y - pozitieStart.y);

            // Prevenim depasirea panoului
            pozitieNoua.x = Math.max(pozitieNoua.x, 0);
            pozitieNoua.y = Math.max(pozitieNoua.y, 0);
            pozitieNoua.x = Math.min(pozitieNoua.x, masaSelectata.getParent().getWidth()
                                        - masaSelectata.getWidth());
            pozitieNoua.y = Math.min(pozitieNoua.y, masaSelectata.getParent().getHeight()
                                        - masaSelectata.getHeight());

            // Prevenim coliziunea
            for (int i = 0; i < Masa.mese.size(); i++) {
                Rectangle masaInViitor = new Rectangle(pozitieNoua.x, pozitieNoua.y,
                                                        Masa.latura, Masa.latura);
                
                if (masaInViitor.intersects(Masa.mese.get(i).buton.getBounds())
                        & (Masa.mese.get(i).buton != masaSelectata) ) {
                    return;
                }
            }

            // Actualizare locatie
            pozitieStart = pozitieMouse;
            masaSelectata.setLocation(pozitieNoua);
            BazaDeDate.actualizeazaPozitieMasa(pozitieNoua, pozitieVeche);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}