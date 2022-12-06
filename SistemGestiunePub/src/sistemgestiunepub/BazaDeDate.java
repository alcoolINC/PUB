/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.awt.Point;
import static java.lang.Integer.parseInt;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class BazaDeDate {

    private static Connection con;

    private BazaDeDate() {
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getInstanta() throws ClassNotFoundException, SQLException {
        if (con == null) {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/pub";
            String user = "root";
            String parola = "";

            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, user, parola);
        }
        return con;
    }

    public static int autentificare(String user, String parola) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "SELECT username, parola, rol, id FROM Utilizator WHERE username = ? AND parola = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, parola);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Daca a returnat ceva query-ul
                FereastraLogin.idUser = rs.getInt(4);
                int rol = rs.getInt(3);
                if (rol != 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE BUTON LOGIN");
        }
        return -1;
    }

    public static int returneazaUltimaCheie() {
        int id = -1;
        try {
            Connection con = getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT @@IDENTITY;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                System.out.println("EROARE CHEIE PRIMARA");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE AFLARE CHEIE PRIMARA UTILIZATOR");
        }
        return id;
    }

    public static void genereazaRaport(DefaultTableModel model) {
        try {
            Connection con = getInstanta();
            String sql = "select id_user, sum(total) from Nota group by id_user;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] rand = {rs.getString(1), rs.getString(2)};
                model.addRow(rand);
            }
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ADAUGARE NOTA");
        }
    }
}
