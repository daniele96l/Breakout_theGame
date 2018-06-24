package com.mygdx.game.databasemanagement;

import com.mygdx.game.databasemanagement.Enum.DropType;
import com.mygdx.game.databasemanagement.Enum.TableType;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    Database database = new Database();
    @Test
    void modifyOffline()
    {
        assertEquals("Inserito",database.modify(""+(int)(Math.random()*1000),"Test",500,DropType.INSERT,TableType.OFFLINE));
    }
    @Test
    void printTableOffline()
    {
        assertEquals("Test              500\n\n",database.printTable(TableType.OFFLINE));
    }
    @Test
    void modifyOfflineDelete()
    {
        assertEquals("Eliminato",database.modify(""+(int)(Math.random()*1000),"Test",50,DropType.DROP_PLAYER,TableType.OFFLINE));
    }
    @Test
    void modifyOfflineDeleteAll()
    {
        assertEquals("Eliminati",database.modify(""+(int)(Math.random()*1000),"Test",50,DropType.DROP_ALL,TableType.OFFLINE));
    }









}