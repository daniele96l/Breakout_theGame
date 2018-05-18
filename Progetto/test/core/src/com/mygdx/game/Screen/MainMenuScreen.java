package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import help.Info;



import javax.swing.*;

public class MainMenuScreen implements Screen {
    private Texture menu;
    private Texture playButton;
    private Texture exitButton;
    private Texture multiplayerofflineButton;
    private Texture score;
    private Texture multiplayeronlineButton;
    private int newHeight, newWight;
    BreakGame game;
    private int numeroPlayer;
    private float coeffDimensionale;
    float barreNere = 0;

    public MainMenuScreen(BreakGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        menu = new Texture("menuscreen.jpg");
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        multiplayerofflineButton = new Texture("multiplayeroffline.png");
        multiplayeronlineButton = new Texture("multiplayeronline.png");
        score = new Texture("score.png");



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, 650);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 100);
        game.getBatch().draw(multiplayerofflineButton, Info.larghezza / 2 - multiplayerofflineButton.getWidth() / 2, 520);//immaginibruttissime
        game.getBatch().draw(score,Info.larghezza / 2 - score.getWidth() / 2, 240 );
        game.getBatch().draw(multiplayeronlineButton,Info.larghezza / 2 - multiplayeronlineButton.getWidth() / 2, 380 );

        if(Gdx.input.justTouched()) {

            if (Gdx.input.getX() > (newWight / 2) - (score.getWidth() * coeffDimensionale) / 2 && (Gdx.input.getX() < newWight / 2 + (score.getWidth() * coeffDimensionale) / 2) && (newHeight - Gdx.input.getY() > 240 * coeffDimensionale+ barreNere) && (newHeight - Gdx.input.getY() < 240 * coeffDimensionale + score.getHeight() * coeffDimensionale +  barreNere)) {
                dispose();
                game.setScreen(new ScoreScreen(game));
            }

            if (Gdx.input.getX() > (newWight / 2) - (playButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (exitButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 100 * coeffDimensionale +  barreNere&& (newHeight- Gdx.input.getY() < 100 * coeffDimensionale + exitButton.getHeight() * coeffDimensionale+  barreNere))) {
                    Gdx.app.exit();
            }

            if (Gdx.input.getX() > (newWight/ 2) - (playButton.getWidth() / 2 * coeffDimensionale)  && (Gdx.input.getX() < newWight  + (playButton.getWidth()/ 2) * coeffDimensionale ) && (newHeight - Gdx.input.getY() > 650 * coeffDimensionale +  barreNere&& (newHeight - Gdx.input.getY() < 650 * coeffDimensionale + exitButton.getHeight() * coeffDimensionale+  barreNere))) {
                    OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
                    if (OfflineGameScreen.getPlayerName() != null && !OfflineGameScreen.getPlayerName().isEmpty()) {
                        dispose();
                        game.setScreen(new OfflineGameScreen(game, 1));
                }
            }
            if (Gdx.input.getX() > (newWight/ 2) - (playButton.getWidth() / 2 * coeffDimensionale)  && (Gdx.input.getX() < newWight  + (playButton.getWidth()/ 2) * coeffDimensionale ) && (newHeight - Gdx.input.getY() > 520 * coeffDimensionale+  barreNere && (newHeight - Gdx.input.getY() < 520 * coeffDimensionale + exitButton.getHeight() * coeffDimensionale+  barreNere))) {

                    OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
                    numeroPlayer = (Integer.parseInt(JOptionPane.showInputDialog(null, "Number of player", "Enter the number of player ", 1)));
                    dispose();
                    game.setScreen(new OfflineGameScreen(game, numeroPlayer));

            }

            if (Gdx.input.getX() > (newWight/ 2) - (playButton.getWidth() / 2 * coeffDimensionale)  && (Gdx.input.getX() < newWight  + (playButton.getWidth()/ 2) * coeffDimensionale ) && (newHeight - Gdx.input.getY() > 380 * coeffDimensionale +  barreNere&& (newHeight - Gdx.input.getY() < 380 * coeffDimensionale + exitButton.getHeight() * coeffDimensionale+  barreNere))) {
                dispose();
                game.setScreen(new MultiplayerGameScreen(game));
            }
        }



        game.getBatch().end();


    }

    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;
        barreNere = 0;

        Vector2 size = Scaling.fit.apply(800, 850, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;

        coeffDimensionale = size.y/(float)Info.altezza;

        if(newHeight > size.y )
            barreNere = Math.abs((newHeight - size.y)/2);


        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
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
