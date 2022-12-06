/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Point;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class ModelMasa {

    private static ArrayList<ModelMasa> mese = null;
    private static int latura = 75;
    private static int xImplicit = 100;
    private static int yImplicit = 100;
    private static Color culoareImplicita = Color.GREEN;
    private static Color culoareOcupat = Color.YELLOW;
    private JButton buton;
    private int id;

    public ModelMasa(String text) {
        this(text, xImplicit, yImplicit);
    }

    public ModelMasa(String text, int x, int y) {
        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setBackground(culoareImplicita);
        buton.setText(text);
        buton.setVisible(true);
    }

    public ModelMasa(int id, String numar, int x, int y) {
        this(numar, x, y);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOcupat() {
        this.buton.setBackground(culoareOcupat);
    }

    public void setLiber() {
        this.buton.setBackground(culoareImplicita);
    }

    public static int getLatura() {
        return latura;
    }
    
    public static ArrayList<ModelMasa> getMese() {
        return mese;
    }

    public JButton getButon() {
        return buton;
    }

    int getId() {
        return id;
    }

    public static int getId(JButton cautat) {
        for (ModelMasa masa : mese) {
            if (masa.buton == cautat) {
                return masa.id;
            }
        }
        return -1;
    }

    public static ModelMasa getById(int id) {
        for (ModelMasa masa : mese) {
            if (masa.id == id) {
                return masa;
            }
        }
        return null;
    }

    public static void adaugaInMemorie(ModelMasa masa) {
        mese.add(masa);
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
            String sql = "SELECT id, numar, x, y FROM Masa";
            ResultSet rs = stmt.executeQuery(sql);
            mese = new ArrayList();
            while (rs.next()) {
                // Extragere pozitie masa
                int id = rs.getInt(1);
                String numar = rs.getString(2);
                int x = rs.getInt(3);
                int y = rs.getInt(4);

                // Instantiere masa
                ModelMasa masa = new ModelMasa(id, numar, x, y);
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
            JOptionPane.showMessageDialog(new JFrame(), "EROARE EXTRAGERE MESE DIN BD");
        }
    }

    public static Boolean adaugaInBd(ModelMasa masa) {
        try {
            Connection con = BazaDeDate.getInstanta();
            String sql = "INSERT INTO Masa(numar, x, y) value (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, masa.buton.getText());
            stmt.setInt(2, masa.buton.getX());
            stmt.setInt(3, masa.buton.getY());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ADAUGA MASA IN BD");
            return true;
        }
        return false;
    }

    public static Boolean stergeDinBd(JButton deSters) {
        try {
            java.sql.Connection con = BazaDeDate.getInstanta();
            String sql = "DELETE FROM Masa WHERE x = ? AND y = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, deSters.getX());
            stmt.setInt(2, deSters.getY());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE STERGERE MASA IN BD");
            return true;
        }
        return false;
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
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ACTUALIZARE LOCATIE MASA IN BD");
        }
    }
}
