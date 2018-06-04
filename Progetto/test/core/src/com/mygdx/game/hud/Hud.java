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
 * @author Alberto Schillaci
 * Classe il cui compito è quello di mostrare le label di ogni giocatore dove indicano punteggio, vita e nome.
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    Table table = new Table();

    /**
     * @param players array dei giocatori
     * @param sb batch
     */

    public Hud(ArrayList<Player> players, SpriteBatch sb) {
        viewport = new FitViewport(Info.getInstance().getLarghezza(), Info.getInstance().getAltezza());
        stage = new Stage(viewport, sb);
        fillTable(players, table);
        stage.addActor(table);
    }

    public Hud(SpriteBatch sb,ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives) {
        viewport = new FitViewport(Info.getInstance().getLarghezza(), Info.getInstance().getAltezza());
        stage = new Stage(viewport, sb);
        fillTable(playerNames,scores,lives, table);
        stage.addActor(table);
    }

    /**
     * metodo che permette di costruire la tabella dei punteggi ottenuti dai giocatori durante le partite offline
     *
     * @param players la lista dei giocatori
     * @param table la tabella
     *
     */

    private void fillTable(ArrayList<Player> players, Table table) {
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
     * è il metodo corrispondente per le partite online: in particolare permette di assegnare ai client
     * le informazioni relative ai punteggi.
     * Il metodo permette di tenere traccia dello stato dei giocatori. In particolare se i giocatori sono più di uno
     * vengono disposte le informazioni di ogni singolo giocatore una sotto l'altra a partire dal nome.
     * Quando invece il giocatore è solo uno, il metodo dispone le sue informazioni su una riga sola.
     *
     * @param playerNames ArrayList contenente i nomi dei giocatori partecipanti
     * @param scores ArrayList contenente i punteggi che ottengono i giocatori
     * @param lives ArrayList che contine il numero di vite che resta ai giocatori
     * @param table la classifica
     */

    private void fillTable(ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives, Table table) {
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
