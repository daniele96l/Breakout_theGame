package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import help.Info;

import javax.swing.*;

public class MainMenuScreen implements Screen {
    private Texture menu;
    private Texture playButton;
    private Texture exitButton;
    private Texture multiplayerButton;
    private Texture score;
    BreakGame game;
    private int numeroPlayer;

    public MainMenuScreen(BreakGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        menu = new Texture("menuscreen.jpg");
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        multiplayerButton = new Texture("multiplayer.png");
        score = new Texture("score.png");



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, 620);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 140);
        game.getBatch().draw(multiplayerButton, Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 460);//immaginibruttissime
        game.getBatch().draw(score,Info.larghezza / 2 - multiplayerButton.getWidth() / 2, 300 );

        if (Gdx.input.getX() > Info.larghezza / 2 - score.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + score.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 300 && (Info.altezza - Gdx.input.getY() < 300 + score.getHeight()))) {
            if (Gdx.input.justTouched())
                game.setScreen(new ScoreScreen(game));
        }

        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + exitButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 140 && (Info.altezza - Gdx.input.getY() < 140 + exitButton.getHeight()))) {
            if (Gdx.input.justTouched())
                Gdx.app.exit();
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - playButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + playButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 620 && (Info.altezza - Gdx.input.getY() < 620 + exitButton.getHeight()))) {
            if (Gdx.input.justTouched()) {
                OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
                game.setScreen(new OfflineGameScreen(game,1));
            }
        }
        if (Gdx.input.getX() > Info.larghezza / 2 - multiplayerButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + multiplayerButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 460 && (Info.altezza - Gdx.input.getY() < 460 + multiplayerButton.getHeight()))) {
            if (Gdx.input.justTouched()) {
                OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
                numeroPlayer =(Integer.parseInt(JOptionPane.showInputDialog(null, "Number of player", "Enter the number of player ", 1)));
                game.setScreen(new OfflineGameScreen(game,numeroPlayer));
            }
        }


        game.getBatch().end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
