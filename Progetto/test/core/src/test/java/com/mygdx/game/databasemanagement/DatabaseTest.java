package com.mygdx.game.databasemanagement;

import com.mygdx.game.databasemanagement.Enum.DropType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 *La classe permette il testing del database attraverso l'uso del  framework Junit
 *
 * @author Cotogni
 *
 */
class DatabaseTest {
    Database database = new Database();
    @Test
    void modifyOffline()
    {
        assertEquals("Inserito",database.modify(""+(int)(Math.random()*1000),"Test",500,DropType.INSERT));
    }
    @Test
    void printTableOffline()
    {
        assertEquals("Test              500\n\n",database.printTable());
    }
    @Test
    void modifyOfflineDelete()
    {
        assertEquals("Eliminato",database.modify(""+(int)(Math.random()*1000),"Test",500,DropType.DROP_PLAYER));
    }
    @Test
    void modifyOfflineDeleteAll()
    {
        assertEquals("Eliminati",database.modify(""+(int)(Math.random()*1000),"Test",500,DropType.DROP_ALL));
    }









}