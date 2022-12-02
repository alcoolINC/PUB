/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemgestiunepub;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
     * @return
     * @throws ClassNotFoundException
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
}
