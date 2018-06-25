package com.mygdx.game.databasemanagement.DAOinterface;

import com.mygdx.game.databasemanagement.Enum.DropType;
import com.mygdx.game.databasemanagement.FilesDB;

public interface RawInt {
    void start();
    String modify(FilesDB f, DropType type);
}
