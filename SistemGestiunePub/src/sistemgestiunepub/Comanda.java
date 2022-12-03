/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Comanda {

    public static ArrayList<Comanda> comenzi = new ArrayList();
    
    private int idMasa;
    private int idAngajat;
    private ArrayList<Produs> produse;
    
    public Comanda(int idMasa, int idAngajat) {
        this.idMasa = idMasa;
        this.idAngajat = idAngajat;
        produse = new ArrayList();
    }
    
    public void adaugaProdus(Produs produs) {
        produse.add(produs);
    }
    
    public void stergeComanda() {
        
    }
}
