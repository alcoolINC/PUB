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
public class ControllerUser {

    public static void adauga(String user, String nume, String parola, int rol,
            DefaultTableModel jTable) {

        ModelUser.adaugaInBd(user, parola, nume, rol);
        int id = BazaDeDate.returneazaUltimaCheie();
        ModelUser tmp = new ModelUser(id, user, nume, rol);
        ModelUser.adaugaInMemorie(tmp);
        ModelUser.adaugaInJtable(jTable, tmp);
    }

    public static void sterge(int id, DefaultTableModel jTable, int indexRand) {
        jTable.removeRow(indexRand);
        ModelUser.stergeDinMemorie(id);
        ModelUser.stergeDinBd(id);
    }
}
