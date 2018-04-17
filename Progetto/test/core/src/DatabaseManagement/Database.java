package DatabaseManagement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/*
    Windows users may has problems with JDBC
 */

public class Database {

    public Database() {
    }

    public String start () {
        String s = "";
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            //jdbc:sqlite:<address of database>. The root is the main directory of the intellij project
            String url = "jdbc:sqlite:BbDb.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("Connection established!\nResult set:");
            String query = "SELECT * FROM PLAYERS ORDER BY NAME";
            ResultSet rs = stmt.executeQuery(query);
            // TODO: 17/04/18 Fix check on empity database
            //if (rs.getString("NAME").isEmpty()) {
            //    System.out.println("No result set!");
            //    return s = "No result set!";
            //}
            while (rs.next()) {
                //rs.getString() take as parameter the name or the numeric index of the column
                System.out.println(rs.getString("NAME"));
                s += rs.getString("NAME") + "\n";
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            s = "There is a big problem!";
            return s;
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            s = "There is a huge problem!";
            return s;
        }
        return s;
    }

    public void insert () {
        BufferedReader fr = new BufferedReader(new InputStreamReader(System.in));
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:BbDb.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("Connection established! Enter the name to insert in the database:");
            String a = fr.readLine();
            String query = "INSERT INTO PLAYERS('NAME') VALUES ('" + a +"')";
            stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void drop () {
        BufferedReader fr = new BufferedReader(new InputStreamReader(System.in));
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:BbDb.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("Connection established! Enter the name to remove:");
            String a = fr.readLine();
            String query = "DELETE FROM PLAYERS WHERE NAME = '" + a + "'";
            stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void empity () {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:BbDb.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("Connection established! Enter the name to remove:");
            String query = "DELETE FROM PLAYERS";
            stmt.executeUpdate(query);
            System.out.println("Database empity!");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }
}
