/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerComanda {

    public static void adaugaProdus(ModelProdus produs, int indexComanda,
            DefaultTableModel jTable) {

        ModelComanda.adaugaProdus(indexComanda, produs);
        ModelComanda.adaugaInJtable(jTable, produs);
    }

    public static void plateste(FereastraComanda f) {
        int idUser = FereastraLogin.idUser;
        int idMasa = f.getIdMasa();
        int total = ModelComanda.calculeazaTotal(f.getIndexComanda());
        ModelNota nota = new ModelNota(idUser, idMasa, total);
        nota.adaugaInBd();
    }

    public static void anuleaza(int indexComanda, DefaultTableModel jTable) {
        ModelComanda.get(indexComanda).reseteaza();
        ModelComanda.reseteazaJtable(jTable);
    }

    public static void afiseaza(JButton masa) {
        int id = ModelMasa.getId(masa);
        ModelComanda.afiseaza(id);
    }
}
