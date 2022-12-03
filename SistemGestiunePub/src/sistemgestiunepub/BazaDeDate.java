/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.awt.Point;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

    public static void extrageMese(JPanel panouMese, JFrame fereastra) {
        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Masa";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Extragere pozitie masa
                int x = rs.getInt(2);
                int y = rs.getInt(3);

                // Instantiere masa
                Masa masa = new Masa(x, y, panouMese);

                if (fereastra instanceof FereastraMeseAdmin) {
                    masa.buton.addMouseListener((FereastraMeseAdmin) fereastra);
                    masa.buton.addMouseMotionListener((FereastraMeseAdmin) fereastra);
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("EROARE EXTRAGERE MESE DIN BD");
        }
    }

    public static void adaugaMasa(int x, int y) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "INSERT INTO Masa(x, y) value (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE BUTON ADAUGA MASA");
        }
    }

    public static void stergeMasa(JButton deSters) {
        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            String sql = "DELETE FROM Masa WHERE x = ? AND y = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deSters.getX());
            ps.setInt(2, deSters.getY());
            ps.execute();
            ps.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE STERGERE MASA");
        }
    }

    public static void actualizeazaPozitieMasa(Point pozitieNoua,
                                                Point pozitieVeche) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "UPDATE Masa SET x = ?, y = ? WHERE x = ? AND y = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, pozitieNoua.x);
            stmt.setInt(2, pozitieNoua.y);
            stmt.setInt(3, pozitieVeche.x);
            stmt.setInt(4, pozitieVeche.y);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ACTUALIZARE LOCATIE MASA IN BD");
        }
    }

}
