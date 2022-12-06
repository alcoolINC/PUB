/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static sistemgestiunepub.BazaDeDate.getInstanta;

/**
 *
 * @author User
 */
public class ModelNota {

    private int idUser;
    private int idMasa;
    private int total;

    public ModelNota(int idUser, int idMasa, int total) {
        this.idUser = idUser;
        this.idMasa = idMasa;
        this.total = total;
    }
    
    public void adaugaInBd() {
        try {
            Connection con = getInstanta();
            String sql = "INSERT INTO Nota(id_user, id_masa, total) VALUE (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idMasa);
            stmt.setInt(3, total);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("EROARE ADAUGARE NOTA");
        }
    }
}
