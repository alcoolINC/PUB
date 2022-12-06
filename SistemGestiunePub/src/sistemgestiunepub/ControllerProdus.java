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
        Boolean eroare = ModelProdus.adaugaInBd(nume, pret);
        if (eroare) {
            return;
        }
        int id = BazaDeDate.returneazaUltimaCheie();
        ModelProdus produs = new ModelProdus(id, nume, pret);
        ModelProdus.adaugaInMemorie(produs);
        ModelProdus.adaugaInJtable(produs, jTable);
    }

    public static void modifica(int id, String nume, int pret,
            DefaultTableModel jTable, int indexRand) {

        Boolean eroare = ModelProdus.modificaInBd(id, nume, pret);
        if (eroare) {
            return;
        }
        ModelProdus.modificaInMemorie(id, nume, pret);
        ModelProdus.modificaInJtable(nume, pret, jTable, indexRand);

    }

    public static void sterge(int id, DefaultTableModel jTable, int indexRand) {
        Boolean eroare = ModelProdus.stergeDinBd(id);
        if (eroare) {
            return;
        }
        ModelProdus.stergeDinMemorie(id);
        jTable.removeRow(indexRand);
    }
}
