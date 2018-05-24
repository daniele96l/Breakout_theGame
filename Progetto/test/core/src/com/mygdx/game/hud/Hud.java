package com.mygdx.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Player.Player;
import help.Info;

import java.util.ArrayList;

/**
 * @author Alberto king schillaci
 *  classe il cui compito è quello di mostrare le label di ogni giocatore dove indicano punteggio, vita e nome.
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    Table table;

    /**
     *
     * @param players array dei giocatori
     * @param sb
     */

    public Hud(ArrayList<Player> players, SpriteBatch sb) {
        viewport = new FitViewport(Info.larghezza, Info.altezza);
        stage = new Stage(viewport, sb);
        fillTable(players);
        stage.addActor(table);
    }
    public Hud(SpriteBatch sb,ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives) {
        viewport = new FitViewport(Info.larghezza, Info.altezza);
        stage = new Stage(viewport, sb);
        fillTable(playerNames,scores,lives);
        stage.addActor(table);
    }

    /**
     * metodo che permette di costruire la tabella dei punteggi ottenuti dai giocatori durante le partite offline
     *
     * @param players la lista dei giocatori
     */

    public void fillTable(ArrayList<Player> players) {
        table = new Table();
        table.top();
        table.setFillParent(true);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            if (players.size() > 1) {
                Label label = new Label(String.format("PLAYER: %s\nSCORE: %d \nLIVES: %d", player.getPlayerName(), player.getScore(), player.getLives()),
                        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(1f);
                table.add(label).expandX();

            }
            if (players.size() == 1) {
                Label label = new Label(String.format("PLAYER: %s                   SCORE: %d                  LIVES: %d", player.getPlayerName(), player.getScore(), player.getLives()),
                        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(1f);
                table.add(label).expandX();

            }


        }
    }

    /**
     * è il metodo corrispondente per le partite online: in particolare permette di assegnare ai client le informazioni relative ai punteggi
     *
     * @param playerNames
     * @param scores
     * @param lives
     */

    public void fillTable(ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives) {
        table = new Table();
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
