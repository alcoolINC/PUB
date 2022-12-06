/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class ControllerMasa {

    public static void adauga(ModelMasa masa, FereastraMeseAdmin f,
            JPanel jPanel) {

        ModelMasa.adaugaInBd(masa.buton.getX(), masa.buton.getY());
        masa.setId(BazaDeDate.returneazaUltimaCheie());
        masa.buton.addMouseListener(f);
        masa.buton.addMouseMotionListener(f);
        ModelMasa.adaugaInMemorie(masa);
        ModelMasa.adaugaInPanou(masa.buton, jPanel);
    }

    public static void sterge(JButton masa, JPanel jPanel) {
        ModelMasa.stergeDinBd(masa);
        ModelMasa.stergeDinMemorie(masa);
        ModelMasa.stergeDinPanou(masa, jPanel);
    }
}
