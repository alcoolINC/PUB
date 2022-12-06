/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import static java.lang.Integer.parseInt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ModelProdus {

    private String nume;
    private int pret;
    private int id;

    private static ArrayList<ModelProdus> produse = null;

    public ModelProdus(int id, String nume, int pret) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
    }

    public static ModelProdus get(int index) {
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

    public static void adaugaInMemorie(ModelProdus produs) {
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

    public static void stergeDinMemorie(int id) {
        int index = getIndex(id);
        if (index == -1) {
            return;
        }
        produse.remove(index);
    }

    public static void modificaInMemorie(int id, String nume, int pret) {
        int index = getIndex(id);
        if (index == -1) {
            return;
        }
        produse.get(index).nume = nume;
        produse.get(index).pret = pret;
    }

    public static void citesteDinBd() {
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
                adaugaInMemorie(new ModelProdus(id, nume, pret));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE EXTRAGERE PRODUSE");
        }
    }

    public static void completeazaJtable(DefaultTableModel jTable) {
        for (ModelProdus produs : produse) {
            adaugaInJtable(produs, jTable);
        }
    }

    public static void adaugaInJtable(ModelProdus produs, DefaultTableModel jTable) {
        String[] rand = {String.valueOf(produs.id), produs.nume,
            String.valueOf(produs.pret)};
        jTable.addRow(rand);
    }

    public static Boolean adaugaInBd(String nume, int pret) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "INSERT INTO Produs(nume, pret) VALUE (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ADAUGARE PRODUS");
            return true;
        }
        return false;
    }

    public static Boolean stergeDinBd(int id) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "DELETE FROM Produs WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE STEGERE PRODUS");
            return true;
        }
        return false;
    }

    public static Boolean modificaInBd(int id, String nume, int pret) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "UPDATE Produs SET nume = ?, pret = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.setInt(3, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ACTUALIZARE PRODUS");
            return true;
        }
        return false;
    }

    public static void modificaInJtable(String nume, int pret,
            DefaultTableModel jTable, int indexRand) {
        
        jTable.setValueAt(nume, indexRand, 1);
        jTable.setValueAt(pret, indexRand, 2);
        jTable.fireTableDataChanged();
    }
}
