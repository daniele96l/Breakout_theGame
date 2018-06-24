package com.mygdx.game.databasemanagement.DAOinterface;

import com.mygdx.game.databasemanagement.Enum.DropType;
import java.util.ArrayList;

/**
 * Interfaccia che contiene i metodi della classe Database
 *
 * @author Curcio
 */

public interface DaoDB {
    void start();
    String printTable ();
    void check(StringBuilder sb);
    String modify(String id, String name, int points, DropType type);
    ArrayList<String> getListaGiocatori();
}
