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

    public static void adaugaProdus(ModelComanda comanda, ModelProdus produs) {
        comanda.produse.add(produs);
    }

    public static int calculeazaTotal(ModelComanda comanda) {
        int total = 0;
        for (ModelProdus produs : comanda.produse) {
            total += produs.getPret();
        }
        return total;
    }

    public static ModelComanda getByIdMasa(int idMasa) {
        for (ModelComanda comanda : comenzi) {
            if (comanda.idMasa == idMasa) {
                return comanda;
            }
        }
        return null;
    }

    public void reseteaza() {
        produse = new ArrayList();
    }

    public static void afiseaza(int idMasa) {
        // Creeaza o fereastra
        FereastraComanda f = new FereastraComanda();
        f.setTitle(String.valueOf(idMasa));
        f.setAlwaysOnTop(true);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setVisible(true);
        // Identifica comanda dupa id-ul mesei
        ModelComanda comanda = getByIdMasa(idMasa);
        // Incarcare date despre comanda
        completeazaJtable((DefaultTableModel) f.getModelJtableComanda(), comanda);
        /*
            Nu se poate face in constructorul ferestrei
            pt ca trebuie instantiata fereastra in primul rand
            numai dupa aceea se poate extrage id-ul mesei si al comenzii din title
         */
        // Actualizeaza total
        JTextField campTotal = f.getJtextFieldTotal();
        int total = calculeazaTotal(comanda);
        campTotal.setText(String.valueOf(total));
    }

    public static void completeazaJtable(DefaultTableModel jTable, ModelComanda comanda) {
        for (ModelProdus produs : comanda.produse) {
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
