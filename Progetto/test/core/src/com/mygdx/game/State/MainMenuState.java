package com.mygdx.game.State;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import help.GameState;
import help.Info;

import javax.swing.*;

public class MainMenuState {

    private Texture menu;
    private Texture playButton;
    private Texture exitButton;
    private Texture multiplayerButton;
    private Texture score;
    static private boolean ok;

    private String text;
    private GameState gamestate;
    private String playerName;
    private MyGdxGame game;

    public MainMenuState( GameState gameState,MyGdxGame game)  {
        this.gamestate = gameState;
        menu = new Texture("menuscreen.jpg");
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        multiplayerButton = new Texture("multiplayer.png");
        score = new Texture("score.png");
        this.game = game;
    }


    public GameState draw(SpriteBatch batch){


        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(menu, 0, 0);
        batch.draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, 620);//alpostodimetterlicosipossousaredellecostanti
        batch.draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 140);
        batch.draw(multiplayerButton, Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 460);//immaginibruttissime
        batch.draw(score,Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 300 );

        if (Gdx.input.getX() > Info.larghezza / 2 - score.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + score.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 300 && (Info.altezza - Gdx.input.getY() < 300 + score.getHeight()))) {
            if (Gdx.input.justTouched())
                return GameState.SCORE;
        }

        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + exitButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 140 && (Info.altezza - Gdx.input.getY() < 140 + exitButton.getHeight()))) {
            if (Gdx.input.justTouched())
                Gdx.app.exit();
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + playButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 620 && (Info.altezza - Gdx.input.getY() < 620 + exitButton.getHeight()))) {
            if (Gdx.input.justTouched()) {
                playerName= JOptionPane.showInputDialog(null, "Nickname", "Enter a nickname", 1);
                game.setLivelloCorrente(1);
                game.reset();
                game.setMultiplayer(false);
                return GameState.ACTION;
            }
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - multiplayerButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + multiplayerButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 460 && (Info.altezza - Gdx.input.getY() < 460 + multiplayerButton.getHeight()))) {
            if (Gdx.input.justTouched()) {
                game.reset();
                return GameState.MULTIPLAYER;
            }
        }
        return GameState.MENU;
    }

    public String getPlayerName() {
        return playerName;
    }
}
