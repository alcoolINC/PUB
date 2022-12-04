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

    public static void adaugaNota(Nota nota) {
        try {
            Connection con = getInstanta();
            String sql = "INSERT INTO Nota(id_user, id_masa, total) VALUE (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, nota.getIdUser());
            stmt.setInt(2, nota.getIdMasa());
            stmt.setInt(3, nota.getTotal());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ADAUGARE NOTA");
        }
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
