/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class Masa {

    public static ArrayList<Masa> mese = null;
    public static int latura = 75;
    public JButton buton;
    private int id;

    public Masa(int x, int y, JPanel panou) {
        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setVisible(true);
    }

    public Masa(int id, int x, int y, JPanel panou) {
        this(x, y, panou);
        this.id = id;
        buton.setText(String.valueOf(id));
    }

    public static void adauga(Masa masa) {
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
        for (Masa i : mese) {
            if (i.buton == cautat) {
                return i.id;
            }
        }
        return -1;
    }

    public static void sterge(JButton deSters, JPanel panou) {
        panou.remove(deSters);
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

    public static void citeste(JPanel panouMese, JFrame fereastra) {
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
                Masa masa = new Masa(id, x, y, panouMese);
                adauga(masa);
                panouMese.add(masa.buton);

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
}
