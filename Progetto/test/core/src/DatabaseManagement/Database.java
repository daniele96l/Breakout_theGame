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
            while (rs.next()) {
                //rs.getString() take as parameter the name or the numeric index of the column
                System.out.println(rs.getString("NAME"));
                s += rs.getString("NAME") + "\n";
            }
            conn.close();
            rs.close();
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
}
