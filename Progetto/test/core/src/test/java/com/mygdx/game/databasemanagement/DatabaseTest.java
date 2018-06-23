package com.mygdx.game.databasemanagement;

import com.mygdx.game.databasemanagement.Enum.DropType;
import com.mygdx.game.databasemanagement.Enum.TableType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {


    @Test
    void printTable()
    {
        Database database =  new Database();

    }

    @Test
    void modify()
    {
        Database database = new Database();
        assertEquals("Inserito",database.modify("12","Marco",1,DropType.INSERT,TableType.OFFLINE));
    }
}