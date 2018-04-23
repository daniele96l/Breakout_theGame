package com.mygdx.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import help.GameState;
import help.Info;

import java.awt.event.TextEvent;

public class PauseMenuState {

    private SpriteBatch batch;
    private GameState gameState;
    private Texture menu;
    private Texture resumeButton;
    private Texture exitButton;
    private Texture menuButton;
    private MyGdxGame game;

    public PauseMenuState(SpriteBatch batch, GameState gameState) {
        this.batch = batch;
        this.gameState = gameState;
        menu = new Texture("menuscreen.jpg");
        menuButton = new Texture("menu.png");
        resumeButton = new Texture("resume.png");
        exitButton = new Texture("exit.png");
    }


    public GameState draw(){

        batch.draw(menu, 0, 0);
        batch.draw(resumeButton, Info.larghezza / 2 - resumeButton.getWidth() / 2, 550);//alpostodimetterlicosipossousaredellecostanti
        batch.draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 150);
        batch.draw(menuButton, Info.larghezza / 2 - menuButton.getWidth() / 2, 350);


        if (Gdx.input.getX() > Info.larghezza / 2 - exitButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + exitButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 150 && (Info.altezza - Gdx.input.getY() < 150 + exitButton.getHeight()))) {
            if (Gdx.input.isTouched())
                Gdx.app.exit();
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - resumeButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + resumeButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 550 && (Info.altezza - Gdx.input.getY() < 550 + resumeButton.getHeight()))) {
            if (Gdx.input.isTouched())
                return GameState.ACTION;
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - menuButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + menuButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 350 && (Info.altezza - Gdx.input.getY() < 350 + menuButton.getHeight()))) {
            if (Gdx.input.isTouched()) {

                return GameState.MENU;

            }
        }

        return GameState.PAUSE;
    }
}
