package com.mygdx.game.databasemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che crea la connessione al database
 *
 * @author Curcio
 */

public class ConnectionSQL {
    /**
     * Metodo che crea la connessione al database attraverso il driver JDBC
     *
     * @return conn
     */
    public static Connection createConnection () {
        Connection conn = null;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
