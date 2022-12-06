/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ModelComanda {

    private static ArrayList<ModelComanda> comenzi = null;

    private int idMasa;
    private int idAngajat;
    private ArrayList<ModelProdus> produse;

    public ModelComanda(int idMasa, int idAngajat) {
        this.idMasa = idMasa;
        this.idAngajat = idAngajat;
        produse = new ArrayList();
    }

    public static void initializeaza() {
        if (comenzi != null) {
            return;
        }

        comenzi = new ArrayList();
        for (ModelMasa masa : ModelMasa.mese) {
            comenzi.add(new ModelComanda(masa.getId(), FereastraLogin.idUser));
        }
    }

    public static ModelComanda get(int index) {
        return comenzi.get(index);
    }

    public static int getIndex(int idMasa) {
        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).idMasa == idMasa) {
                return i;
            }
        }
        return -1;
    }

    public static void adaugaProdus(int index, ModelProdus produs) {
        comenzi.get(index).produse.add(produs);
    }

    public static int calculeazaTotal(int index) {
        int total = 0;
        ArrayList<ModelProdus> produse = comenzi.get(index).produse;
        for (ModelProdus produs : produse) {
            total += produs.getPret();
        }
        return total;
    }

    public void reseteaza() {
        produse = new ArrayList();
    }

    public static void afiseaza(int idMasa) {
        // Extrage indexul comenzii in functie de id-ul mesei
        int indexComanda = getIndex(idMasa);
        // Creeaza o fereastra
        FereastraComanda f = new FereastraComanda();
        f.setTitle(String.valueOf(idMasa));
        f.setAlwaysOnTop(true);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setVisible(true);
        // Incarcare date despre comanda
        DefaultTableModel modelJtable = f.getModelJtableComanda();
        completeazaJtable(modelJtable, indexComanda);
        /*
            Nu se poate face in constructorul ferestrei
            pt ca trebuie instantiata fereastra in primul rand
            numai dupa aceea se poate extrage id-ul mesei si al comenzii din title
         */

        JTextField campTotal = f.getJtextFieldTotal();
        int total = calculeazaTotal(indexComanda);
        campTotal.setText(String.valueOf(total));
    }

    public static void completeazaJtable(DefaultTableModel jTable, int index) {
        ArrayList<ModelProdus> produse = comenzi.get(index).produse;
        for (ModelProdus produs : produse) {
            adaugaInJtable(jTable, produs);
        }
    }

    public static void adaugaInJtable(DefaultTableModel jTable, ModelProdus produs) {
        String rand[] = {String.valueOf(produs.getId()), produs.getNume(),
            String.valueOf(produs.getPret())};
        jTable.addRow(rand);
    }

    public static void reseteazaJtable(DefaultTableModel jTable) {
        int nrRanduri = jTable.getRowCount();
        for (int i = nrRanduri - 1; i >= 0; i--) {
            jTable.removeRow(i);
        }
    }
}
