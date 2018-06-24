package com.mygdx.game.databasemanagement;

import com.mygdx.game.databasemanagement.Enum.DropType;
import com.mygdx.game.databasemanagement.Enum.TableType;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe che implementa la gestione del database precedentemente creato. Il DBMS è sqlite. Questa classe mostra solo 3
 * metodi: 2 per stampare i nicknames nella sezione score del gioco e il restante che serve per inserire o rimuovere tuple
 * o coppie di tuple dal database.
 *
 * @author Curcio
 */

public class Database {
    private ArrayList<String> listaGiocatoriOff;
    private ArrayList<String> listaGiocatoriOn;

    public Database() {
        listaGiocatoriOff = new ArrayList<String>();
        listaGiocatoriOn = new ArrayList<String>();
    }

    /**
     * In questo metodo viene interrogato il database. Viene caricato il driver JDBC precedentemente aggiunto alle librerie,
     * creata la connessione ed eseguita la query. Successivamente i record letti verranno aggiunti ad un ArrayList in modo
     * da stampare i primi dieci nella sezione "score". Le tuple vengono lette direttamente in ordine decrescente di punteggio.
     * Sono gestite le com.mygdx.game.eccezioni: SQLException e ClassNotFoundException.
     *
     * @param tableType é un enum che serve a distinguere quali record, ONLINE o OFFLINE, si vogliono leggere.
     */
    private void start(TableType tableType) {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            //The main path is asse
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            switch (tableType) {
                case ONLINE:
                    String queryO = "SELECT * FROM ONLINE ORDER BY POINTS DESC";
                    ResultSet rsO = stmt.executeQuery(queryO);
                    while (rsO.next()) {
                        listaGiocatoriOn.add(rsO.getString("NICKNAME") + "  " + rsO.getString("POINTS") + "\n\n");
                    }
                    break;
                case OFFLINE:
                    String query = "SELECT * FROM GAMES ORDER BY POINTS DESC";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        listaGiocatoriOff.add(rs.getString("NICKNAME") + "              " + rs.getString("POINTS") + "\n\n");
                    }
                    break;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }

    /**
     * Metodo che serve a stampare i primi dieci giocatori della modalità OFFLINE in ordine di punteggio. Se l'Arraylist
     * è vuoto vuol dire che non ha ancora giocato nessuno e verrà visualizzato il relativo messaggio d'errore. Se la
     * dimensione dell'ArrayList è maggiore di 10 memorizzo nella variabile che verrà visualizzata nella sezione score
     * solo i primi 10.
     *
     * @return sOff: è la stringa che contiene o i nicknames in ordine di punteggio o il messaggio di errore nel caso in cui
     *               l'ArrayList risultasse vuoto.
     */
    public String printTable (TableType tableType) {
        start(tableType);
        StringBuilder sOff = new StringBuilder();
        StringBuilder sOn = new StringBuilder();

        if (tableType.equals(TableType.OFFLINE)) {
            check(sOff);
            return sOff.toString();
        }
        if (tableType.equals(TableType.ONLINE)) {
            check(sOn);
            return sOn.toString();
        }
        return null;
    }

    /**
     *
     * @param sb: StringBuilder da utilizzare
     */

    private void check(StringBuilder sb) {
        if (listaGiocatoriOff.size() < 10) {
            if (listaGiocatoriOff.size() == 0) {
                sb.append("NO SCORE");
            } else {
                for (int i = 0; i < listaGiocatoriOff.size(); i++) {
                    sb.append(listaGiocatoriOff.get(i));
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                sb.append(listaGiocatoriOff.get(i));
            }
        }
    }

    /**
     * Metodo che dopo aver caricato il driver e creata la connessione permette di inserire o eliminare tuple o coppie di
     * tuple.
     *
     * @param id: numero randomico compreso tra 0 e 1000 generato nella classe "offlinegamescreen" che serve a identificare
     *            univocamnete le varie partite giocate.
     * @param name: nome del giocatore
     * @param points: punteggio del giocatore in questione
     * @param type: parametro che serve a scegliere il tipo di operazione da eseguire
     */
    public String modify(String id, String name, int points, DropType type, TableType tableType) {
        String query;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            if (tableType.equals(TableType.OFFLINE)) {
                switch (type) {
                    case INSERT:
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO GAMES VALUES (?, ?, ?)");
                        stmt.setString(1, id);
                        stmt.setString(2, name);
                        stmt.setInt(3, points);
                        stmt.executeUpdate();
                        conn.close();
                        return "Inserito";
                    case DROP_PLAYER:
                        Statement stm = conn.createStatement();
                        query = "DELETE FROM GAMES WHERE NICKNAME = '" + name + "'";
                        stm.executeUpdate(query);
                        conn.close();
                        return "Eliminato";
                    case DROP_ALL:
                        Statement st = conn.createStatement();
                        query = "DELETE FROM GAMES";
                        st.executeUpdate(query);
                        conn.close();
                        return "Eliminati";
                }
            }
            if (tableType.equals(TableType.ONLINE)) {
                switch (type) {
                    case INSERT:
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO ONLINE VALUES (?, ?, ?)");
                        stmt.setString(1, id);
                        stmt.setString(2, name);
                        stmt.setInt(3, points);
                        stmt.executeUpdate();
                        conn.close();
                        return "Inserito";
                    case DROP_PLAYER:
                        Statement stm = conn.createStatement();
                        query = "DELETE FROM ONLINE WHERE NICKNAME = '" + name + "'";
                        stm.executeUpdate(query);
                        conn.close();
                        return "Eliminato";
                    case DROP_ALL:
                        Statement st = conn.createStatement();
                        query = "DELETE FROM ONLINE";
                        st.executeUpdate(query);
                        conn.close();
                        return "Eliminati";
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
        return "Error";
    }

    public ArrayList<String> getListaGiocatoriOff() {
        return listaGiocatoriOff;
    }

    public ArrayList<String> getListaGiocatoriOn() {
        return listaGiocatoriOn;
    }
}
