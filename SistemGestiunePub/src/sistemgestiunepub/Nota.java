/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

/**
 *
 * @author User
 */
public class Nota {
    
    private int idUser;
    private int idMasa;
    private int total;
    
    public Nota(int idUser, int idMasa, int total) {
        this.idUser = idUser;
        this.idMasa = idMasa;
        this.total = total;
    }
    
    public int getIdUser(){return idUser;}
    public int getIdMasa(){return idMasa;}
    public int getTotal(){return total;}
}
