/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.awt.Point;
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
public class ModelMasa {

    public static ArrayList<ModelMasa> mese = null;
    public static int latura = 75;
    public JButton buton;
    private int id;

    public ModelMasa(int x, int y) {
        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setVisible(true);
    }

    public ModelMasa(int id, int x, int y) {
        this(x, y);
        this.id = id;
        buton.setText(String.valueOf(id));
    }

    public static void adaugaInMemorie(ModelMasa masa) {
        mese.add(masa);
    }

    public void setId(int id) {
        this.id = id;
        buton.setText(String.valueOf(id));
    }

    int getId() {
        return id;
    }

    public static int getId(JButton cautat) {
        for (ModelMasa i : mese) {
            if (i.buton == cautat) {
                return i.id;
            }
        }
        return -1;
    }

    public static void stergeDinMemorie(JButton deSters) {
        for (int i = 0; i < mese.size(); i++) {
            if (mese.get(i).buton == deSters) {
                mese.remove(i);
                return;
            }
        }
    }

    public static Boolean seSuprapune(JButton masa) {
        for (int i = 0; i < mese.size(); i++) {
            if (masa.getBounds().intersects(mese.get(i).buton.getBounds())
                    & (masa != mese.get(i).buton)) {
                return true;
            }
        }
        return false;
    }

    public static void adaugaInPanou(JButton masa, JPanel jPanel) {
        jPanel.add(masa);
        jPanel.revalidate();
        jPanel.repaint();
    }

    public static void stergeDinPanou(JButton masa, JPanel jPanel) {
        jPanel.remove(masa);
        jPanel.revalidate();
        jPanel.repaint();
    }

    public static void citesteDinBd(JPanel panouMese, JFrame fereastra) {
        if (mese != null) {
            return;
        }

        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, x, y FROM Masa";
            ResultSet rs = stmt.executeQuery(sql);
            mese = new ArrayList();
            while (rs.next()) {
                // Extragere pozitie masa
                int id = rs.getInt(1);
                int x = rs.getInt(2);
                int y = rs.getInt(3);

                // Instantiere masa
                ModelMasa masa = new ModelMasa(id, x, y);
                adaugaInMemorie(masa);
                ModelMasa.adaugaInPanou(masa.buton, panouMese);

                if (fereastra instanceof FereastraMeseAdmin) {
                    // Fereastra admin
                    masa.buton.addMouseListener((FereastraMeseAdmin) fereastra);
                    masa.buton.addMouseMotionListener((FereastraMeseAdmin) fereastra);
                } else {
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

    public static void adaugaInBd(int x, int y) {
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

    public static void stergeDinBd(JButton deSters) {
        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            String sql = "DELETE FROM Masa WHERE x = ? AND y = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, deSters.getX());
            stmt.setInt(2, deSters.getY());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE STERGERE MASA");
        }
    }

    public static void actualizeazaPozitieInBd(Point pozitieNoua,
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
