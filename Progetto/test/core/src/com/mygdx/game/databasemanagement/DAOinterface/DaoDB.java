package com.mygdx.game.databasemanagement.DAOinterface;

import com.mygdx.game.databasemanagement.Enum.DropType;
import com.mygdx.game.databasemanagement.FilesDB;

import java.util.ArrayList;

/**
 * Interfaccia che contiene i metodi della classe Database
 *
 * @author Curcio
 */

public interface DaoDB extends RawInt {
    void start();
    String printTable ();
    void check(StringBuilder sb);
    String modify(FilesDB f, DropType type);
    ArrayList<String> getListaGiocatori();
}
