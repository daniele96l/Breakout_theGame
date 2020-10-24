package com.mygdx.game.databasemanagement;

import com.mygdx.game.databasemanagement.Enum.DropType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *La classe permette il testing del database attraverso l'uso del  framework Junit
 *
 * @author Cotogni
 *
 */
public class DatabaseTest {
    Database database = new Database();
    @Test
    public void modifyOffline()
    {
        assertEquals("Inserito",database.modify(new FilesDB("" + (int)(Math.random()*1000), "Test", 500), DropType.INSERT));
    }
    @Test
    public void printTableOffline()
    {
        assertEquals("Test              500\n\n",database.printTable());
    }
    @Test
    public void modifyOfflineDelete()
    {
        assertEquals("Eliminato",database.modify(new FilesDB("" + (int)(Math.random()*1000), "Test", 500), DropType.DROP_PLAYER));
    }
    @Test
    public void modifyOfflineDeleteAll()
    {
        assertEquals("Eliminati",database.modify(new FilesDB("" + (int)(Math.random()*1000), "Test", 500), DropType.DROP_ALL));
    }









}