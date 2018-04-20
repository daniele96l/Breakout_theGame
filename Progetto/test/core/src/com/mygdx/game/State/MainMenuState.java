package com.mygdx.game.State;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import help.GameState;
import help.Info;

public class MainMenuState  {

    private Texture menu;
    private Texture playButton;
    private Texture exitButton;
    private Texture multiplayerButton;
    private Texture score;

    private SpriteBatch batch;
    private GameState gamestate;

    public MainMenuState(SpriteBatch batch, GameState gameState) {
        this.batch = batch;
        this.gamestate = gameState;
        menu = new Texture("menuscreen.jpg");
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        multiplayerButton = new Texture("multiplayer.png");
        score = new Texture("score.png");
    }


    public GameState draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(menu, 0, 0);
        batch.draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, 530);//alpostodimetterlicosipossousaredellecostanti
        batch.draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 150);
        batch.draw(multiplayerButton, Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 350);//immaginibruttissime
        batch.draw(score,Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 700 );
        if (Gdx.input.getX() > Info.larghezza / 2 - score.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + score.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 700 && (Info.altezza - Gdx.input.getY() < 700 + score.getHeight()))) {
            if (Gdx.input.isTouched())
                return GameState.SCORE;
        }

        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + exitButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 150 && (Info.altezza - Gdx.input.getY() < 150 + exitButton.getHeight()))) {
            if (Gdx.input.isTouched())
                Gdx.app.exit();
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + playButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 550 && (Info.altezza - Gdx.input.getY() < 550 + exitButton.getHeight()))) {
            if (Gdx.input.isTouched()) {
                return GameState.ACTION;
            }
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - multiplayerButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + multiplayerButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 350 && (Info.altezza - Gdx.input.getY() < 350 + multiplayerButton.getHeight()))) {
            if (Gdx.input.isTouched()) {
                return GameState.MULTIPLAYER;
            }
        }
        return GameState.MENU;
    }




}
