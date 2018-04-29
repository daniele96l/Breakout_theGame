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

public class Hud {
    public Stage stage;
    private Viewport viewport;
    Table table;

    public Hud(ArrayList<Player> players, SpriteBatch sb) {
        viewport=new FitViewport(Info.larghezza, Info.altezza);
        stage=new Stage(viewport, sb);
        fillTable(players);
        stage.addActor(table);
    }

    public void fillTable(ArrayList<Player> players) {
        table=new Table();
        table.top();
        table.setFillParent(true);

        for(int i=0; i<players.size(); i++) {
            Player player=players.get(i);
            Label label=new Label(String.format("PLAYER: %s \n SCORE: %d \n LIVES: %d", player.getPlayerName(), player.getScore(), player.getLives()),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            label.setFontScale(1.5f);
            table.add(label).expandX();
        }
    }
}
