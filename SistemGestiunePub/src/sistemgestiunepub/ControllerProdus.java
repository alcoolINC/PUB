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
public class ControllerProdus {
    
    public static void adauga(String nume, int pret, DefaultTableModel jTable) {
        ModelProdus.adaugaInBd(nume, pret);
        int id = BazaDeDate.returneazaUltimaCheie();
        ModelProdus produs = new ModelProdus(id, nume, pret);
        ModelProdus.adaugaInMemorie(produs);
        ModelProdus.adaugaInJtable(produs, jTable);
    }
    
    public static void modifica(int id, String nume, int pret,
            DefaultTableModel jTable, int indexRand) {
       
        ModelProdus.modificaInJtable(nume, pret, jTable, indexRand);
        ModelProdus.modificaInMemorie(id, nume, pret);
        ModelProdus.modificaInBd(id, nume, pret);
    }
    
    public static void sterge(int id, DefaultTableModel jTable, int indexRand) {
        jTable.removeRow(indexRand);
        ModelProdus.stergeDinMemorie(id);
        ModelProdus.stergeDinBd(id);
    }
}
