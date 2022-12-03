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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
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

    public static void extrageMese(JPanel panouMese, JFrame fereastra) {
        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, x, y FROM Masa";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Extragere pozitie masa
                int id = rs.getInt(1);
                int x = rs.getInt(2);
                int y = rs.getInt(3);

                // Instantiere masa
                Masa masa = new Masa(x, y, panouMese);
                masa.setId(id);

                if (fereastra instanceof FereastraMeseAdmin) {
                    // Fereastra admin
                    masa.buton.addMouseListener((FereastraMeseAdmin) fereastra);
                    masa.buton.addMouseMotionListener((FereastraMeseAdmin) fereastra);
                }
                else {
                    // Fereastra angajat
                    masa.buton.addMouseListener((FereastraMeseAngajat) fereastra);
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

    public static void adaugaUtilizator(String user, String parola,
            String nume, String rol) {

        try {
            Connection con = getInstanta();
            String sql = "INSERT INTO Utilizator(username, parola, nume, rol) VALUE (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, parola);
            ps.setString(3, nume);
            ps.setInt(4, parseInt(rol));
            ps.execute();
            ps.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE INSERARE UTILIZATOR");
        }
    }

    public static String returneazaUltimaCheie() {
        String id = "-1";
        try {
            Connection con = getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT @@IDENTITY;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getString(1);
            } else {
                System.out.println("EROARE CHEIE PRIMARA");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE AFLARE CHEIE PRIMARA UTILIZATOR");
        }
        return id;
    }

    public static void extrageUtilizatori(JTable jTable) {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            Connection con = getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, username, nume, rol FROM Utilizator";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String user = rs.getString(2);
                String nume = rs.getString(3);
                String rol = rs.getString(4);
                String[] row = {id, user, nume, rol};
                model.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE EXTRAGERE UTILIZATORI");
        }
    }

    public static void stergeUtilizator(String id) {
        try {
            Connection con = getInstanta();
            String sql = "DELETE FROM Utilizator WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE STEGERE UTILIZATOR");
        }
    }

    public static void extrageProduse(JTable jTable) {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            Connection con = getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, nume, pret FROM Produs";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String nume = rs.getString(2);
                String produs = rs.getString(3);
                String[] row = {id, nume, produs};
                model.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE EXTRAGERE PRODUSE");
        }
    }

    public static void adaugaProdus(String nume, String pret) {
        try {
            Connection con = getInstanta();
            String sql = "insert into Produs(nume, pret) values (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, parseInt(pret));
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ADAUGARE PRODUS");
        }
    }

    public static void stergeProdus(String id) {
        try {
            Connection con = getInstanta();
            String sql = "DELETE FROM Produs WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE STEGERE PRODUS");
        }
    }

    public static void actualizeazaProdus(String id, String nume, String pret) {
        try {
            Connection con = getInstanta();
            String sql = "UPDATE Produs SET nume = ?, pret = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setString(2, pret);
            stmt.setString(3, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ACTUALIZARE PRODUS");
        }
    }
}
