/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Produs {

    private String nume;
    private int pret;
    private int id;

    private static ArrayList<Produs> produse = null;

    public Produs(int id, String nume, int pret) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
    }

    public static Produs get(int index) {
        return produse.get(index);
    }
    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getPret() {
        return pret;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public static void adauga(Produs produs) {
        produse.add(produs);
    }

    public static int getIndex(int id) {
        for (int i = 0; i < produse.size(); i++) {
            if (produse.get(i).id == id) {;
                return i;
            }
        }
        return -1;
    }

    public static void sterge(int id) {
        int index = getIndex(id);
        if (index == -1) {
            return;
        }
        produse.remove(index);
    }

    public static void modifica(int id, String nume, int pret) {
        int index = getIndex(id);
        if (index == -1) {
            return;
        }
        produse.get(index).nume = nume;
        produse.get(index).pret = pret;
    }

    public static void citeste() {
        if (produse != null) {
            return;
        }

        try {
            Connection con = BazaDeDate.getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, nume, pret FROM Produs";
            ResultSet rs = stmt.executeQuery(sql);
            produse = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nume = rs.getString(2);
                int pret = rs.getInt(3);
                produse.add(new Produs(id, nume, pret));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE EXTRAGERE PRODUSE");
        }
    }

    /*
    public static void completeazaJtable(DefaultTableModel model) {
        for (int i = 0; i < produse.size(); i++) {
            int id = produse.get(i).id;
            String nume = produse.get(i).nume;
            int pret = produse.get(i).pret;
            adaugaJtable(model, id, nume, pret);
        }
    }
     */
    public static void completeazaJtable(DefaultTableModel model) {
        for (Produs p : produse) {
            adaugaJtable(model, p);
        }
    }

    /*
    public static void adaugaJtable(DefaultTableModel model, int id,
            String nume, int pret) {
        String[] rand = {String.valueOf(id), nume, String.valueOf(pret)};
        model.addRow(rand);
    }
     */
    public static void adaugaJtable(DefaultTableModel model, Produs produs) {
        String[] rand = {String.valueOf(produs.id), produs.nume,
            String.valueOf(produs.pret)};
        model.addRow(rand);
    }

}
