package DatabaseManagement;

import DatabaseManagement.Enum.DropType;
import java.sql.*;
import java.util.ArrayList;

/*
    You must have jdbc library in the same package of desktop launcher and db in assets folder!
 */

public class Database {
    private ArrayList<String> listaGiocatori;

    public Database() {
    }

    private boolean check() {
        boolean empity = true;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //I use this query and not "SELECT COUNT..." because the last method returns always a result set, while
            //now if there aren't tuple rs.next() is false!
            String query = "SELECT * FROM GAMES";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                empity = false;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
        return empity;
    }

    private String printTable () {
        String s = "";
        if (listaGiocatori.size() < 10) {
            for (int i = 0; i < listaGiocatori.size(); i++) {
                s += listaGiocatori.get(i);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                s += listaGiocatori.get(i);
            }
        }
        return s;
    }

    public String start() {
        listaGiocatori = new ArrayList<String>();
        String s = "";
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            //The main path is assets!
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            if (check()) {
                s += "NO SCORE";
                return s;
            }
            String query = "SELECT * FROM GAMES ORDER BY POINTS DESC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaGiocatori.add(rs.getString("NICKNAME") + "              " + rs.getString("POINTS") + "\n\n");
            }
            return printTable();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            s = "There is a big problem!";
            return s;
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            s = "There is a huge problem!";
            return s;
        }
    }

    public void modify(String id, String name, int points, DropType type) {
        String query;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            switch (type) {
                case INSERT:
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO GAMES VALUES (?, ?, ?)");
                    stmt.setString(1, id);
                    stmt.setString(2, name);
                    stmt.setInt(3, points);
                    stmt.executeUpdate();
                    conn.close();
                    break;
                case DROP_PLAYER:
                    Statement stm = conn.createStatement();
                    query = "DELETE FROM GAMES WHERE NICKNAME = '" + name + "'";
                    stm.executeUpdate(query);
                    conn.close();
                    break;
                case DROP_ALL:
                    Statement st = conn.createStatement();
                    query = "DELETE FROM GAMES";
                    st.executeUpdate(query);
                    conn.close();
                    break;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }
}
