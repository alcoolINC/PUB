/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ModelUser {

    private String user;
    private String nume;
    private int rol;
    private int id;

    private static ArrayList<ModelUser> angajati = null;

    public ModelUser(int id, String user, String nume, int rol) {
        this.id = id;
        this.user = user;
        this.nume = nume;
        this.rol = rol;
    }

    public static void adaugaInMemorie(ModelUser angajat) {
        angajati.add(angajat);
    }

    public static int getIndex(int id) {
        for (int i = 0; i < angajati.size(); i++) {
            if (angajati.get(i).id == id) {
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
        angajati.remove(index);
    }

    public static void citesteDinBd() {
        if (angajati != null) {
            return;
        }

        try {
            Connection con = BazaDeDate.getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, username, nume, rol FROM Utilizator";
            ResultSet rs = stmt.executeQuery(sql);
            angajati = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String user = rs.getString(2);
                String nume = rs.getString(3);
                int rol = rs.getInt(4);
                adaugaInMemorie(new ModelUser(id, user, nume, rol));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE EXTRAGERE ANGAJAT");
        }
    }

    public static void adaugaInBd(String user, String parola,
            String nume, int rol) {

        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "INSERT INTO Utilizator(username, parola, nume, rol) VALUE (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, parola);
            stmt.setString(3, nume);
            stmt.setInt(4, rol);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE INSERARE UTILIZATOR");
        }
    }

    public static void stergeDinBd(int id) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "DELETE FROM Utilizator WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE STEGERE UTILIZATOR");
        }
    }

    public static void completeazaJtable(DefaultTableModel model) {
        for (ModelUser angajat : angajati) {
            adaugaInJtable(model, angajat);
        }
    }

    public static void adaugaInJtable(DefaultTableModel model, ModelUser angajat) {
        String[] rand = {String.valueOf(angajat.id), angajat.user, angajat.nume,
            String.valueOf(angajat.rol)};
        model.addRow(rand);
    }
}
