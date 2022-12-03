/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class Masa {

    public static int latura = 75;
    public static ArrayList<Masa> mese = new ArrayList();

    public JButton buton;

    public Masa(int x, int y, JPanel panou) {

        mese.add(this);

        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setVisible(true);

        panou.add(buton);
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

    public static Boolean verifica(JButton masa) {
        for (int i = 0; i < mese.size(); i++) {
            if (masa.getBounds().intersects(mese.get(i).buton.getBounds())
                    & (masa != mese.get(i).buton) ) {
                System.out.println("Exista alta masa in pozitia de spawn.");
                return true;
            }
        }
        return false;
    }
}
