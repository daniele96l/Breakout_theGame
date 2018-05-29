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
     * @param sb
     */

    public Hud(ArrayList<Player> players, SpriteBatch sb) {
        viewport = new FitViewport(Info.larghezza, Info.altezza);
        stage = new Stage(viewport, sb);
        FillTable.fillTable(players, table);
        stage.addActor(table);
    }

    public Hud(SpriteBatch sb,ArrayList<String> playerNames, ArrayList<String> scores, ArrayList<String> lives) {
        viewport = new FitViewport(Info.larghezza, Info.altezza);
        stage = new Stage(viewport, sb);
        FillTable.fillTable(playerNames,scores,lives, table);
        stage.addActor(table);
    }
}
