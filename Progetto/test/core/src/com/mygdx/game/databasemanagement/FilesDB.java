package com.mygdx.game.databasemanagement;

/**
 * La classe serve per contenere e trattare i dati del db
 *
 * @author Curcio
 */

public class FilesDB {
    private String id, nicknames;
    private int points;

    public FilesDB(String id, String nicknames, int points) {
        this.id = id;
        this.nicknames = nicknames;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getNicknames() {
        return nicknames;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return (nicknames + "        " + points);
    }
}
