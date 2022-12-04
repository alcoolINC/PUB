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
public class Comanda {

    private static ArrayList<Comanda> comenzi = null;

    private int idMasa;
    private int idAngajat;
    private ArrayList<Produs> produse;

    public Comanda(int idMasa, int idAngajat) {
        this.idMasa = idMasa;
        this.idAngajat = idAngajat;
        produse = new ArrayList();
    }

    public static Comanda get(int index) {
        return comenzi.get(index);
    }

    public static void adaugaProdus(int index, Produs produs) {
        comenzi.get(index).produse.add(produs);
    }

    public static int calculeazaTotal(int index) {
        int total = 0;
        ArrayList<Produs> produse = comenzi.get(index).produse;
        for (Produs produs : produse) {
            total += produs.getPret();
        }
        return total;
    }

    public void reseteaza() {
        produse = new ArrayList();
    }

    public static void initializeaza() {
        if (comenzi != null) {
            return;
        }

        comenzi = new ArrayList();
        for (Masa masa : Masa.mese) {
            comenzi.add(new Comanda(masa.getId(), FereastraLogin.idUser));
        }
    }

    public static void afiseaza(int idMasa) {
        int indexComanda = getIndex(idMasa);

        FereastraComanda f = new FereastraComanda();
        f.setTitle(String.valueOf(idMasa));
        f.setAlwaysOnTop(true);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setVisible(true);
        // Incarcare date despre comanda
        DefaultTableModel model = f.getModelJtableComanda();
        Comanda.completeazaJtable(model, indexComanda);
        /*
            Nu se poate face in constructorul ferestrei
            pt ca trebuie instantiata fereastra in primul rand
            numai dupa aceea se poate extrage id-ul mesei si al comenzii din title
         */

        JTextField campTotal = f.getJtextFieldTotal();
        int total = Comanda.calculeazaTotal(indexComanda);
        campTotal.setText(String.valueOf(total));
    }

    public static void completeazaJtable(DefaultTableModel model, int index) {
        ArrayList<Produs> produse = comenzi.get(index).produse;
        for (Produs produs : produse) {
            adaugaJtable(model, produs);
        }
    }

    public static void adaugaJtable(DefaultTableModel model, Produs produs) {
        String rand[] = {String.valueOf(produs.getId()), produs.getNume(),
            String.valueOf(produs.getPret())};
        model.addRow(rand);
    }

    public static int getIndex(int idMasa) {
        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).idMasa == idMasa) {
                return i;
            }
        }
        return -1;
    }
}
