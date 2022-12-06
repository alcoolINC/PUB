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

        Boolean eroare = ModelMasa.adaugaInBd(masa);
        if (eroare) {
            return;
        }
        masa.setId(BazaDeDate.returneazaUltimaCheie());
        masa.getButon().addMouseListener(f);
        masa.getButon().addMouseMotionListener(f);
        ModelMasa.adaugaInMemorie(masa);
        ModelMasa.adaugaInPanou(masa.getButon(), jPanel);
    }

    public static void sterge(JButton masa, JPanel jPanel) {
        Boolean eroare = ModelMasa.stergeDinBd(masa);
        if (eroare) {
            return;
        }
        ModelMasa.stergeDinMemorie(masa);
        ModelMasa.stergeDinPanou(masa, jPanel);
    }
}
