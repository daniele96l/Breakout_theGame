/*
* DA FARE:
*
* Valutare se ha senso metterla Singleton
*
* Non so se la documentazione sia completa
*
*
*
*
*
* */

package com.mygdx.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Player.Player;

import java.util.ArrayList;

public class FillTable {
    /**
     * metodo che permette di costruire la tabella dei punteggi ottenuti dai giocatori durante le partite offline
     *
     * @param players la lista dei giocatori
     * @param table
     *
     * @see: filltable(ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives, Table table)
     */

    public static void fillTable(ArrayList<Player> players, Table table) {
        table.top();
        table.setFillParent(true);

        ArrayList<String> playerNames=new ArrayList<String>();
        ArrayList<String> scores=new ArrayList<String>();
        ArrayList<String> lives=new ArrayList<String>();

        for(Player player:players) {
            playerNames.add(player.getPlayerName());
            scores.add(""+player.getScore());
            lives.add(""+player.getLives());
        }

        fillTable(playerNames,scores,lives,table);
    }

    /**
     * è il metodo corrispondente per le partite online: in particolare permette di assegnare ai client le informazioni relative ai punteggi
     *
     * @param playerNames
     * @param scores
     * @param lives
     */

    public static void fillTable(ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives, Table table) {
        table.top();
        table.setFillParent(true);

        for (int i = 0; i < playerNames.size(); i++) {

            if (playerNames.size() > 1) {
                Label label = new Label(String.format("PLAYER: %s\nSCORE: %d \nLIVES: %d", playerNames.get(i), Integer.parseInt(scores.get(i)), Integer.parseInt(lives.get(i))),
                        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(1f);
                table.add(label).expandX();

            }
            if (playerNames.size() == 1) {
                Label label = new Label(String.format("PLAYER: %s                   SCORE: %d                  LIVES: %d", playerNames.get(i), Integer.parseInt(scores.get(i)), Integer.parseInt(lives.get(i))),
                        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(1f);
                table.add(label).expandX();

            }


        }
    }
}
