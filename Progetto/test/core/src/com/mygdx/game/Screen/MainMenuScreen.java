package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import help.Info;

/**
 * @autor ligato,schillaci, regna
 * La classe che gestisce il memù principale con tutti i suoi bottoni
 *
 */
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
    ScreenHandler screenHandler;
    private int playbutton = 510+20;
    private int onlinebutton = 390+20;
    private int offlinebutton = 270+20;
    private int scorebutton = 150+20;
    private int exitbutton = 30+20;
    private Drawer drawer;
    private Resizer resizer;
    float tempVet[];

    public MainMenuScreen(BreakGame game) {
        this.game = game;
        screenHandler = new ScreenHandler();

    }

    /**
     * Associa alle variabili le immagini che dovranno essere renderizate
     */
    @Override
    public void show() {
        menu = new Texture("menuscreen.jpg");
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        multiplayerofflineButton = new Texture("multiplayeroffline.png");
        multiplayeronlineButton = new Texture("multiplayeronline.png");
        score = new Texture("score.png");
        resizer = new Resizer();
        tempVet = new float[2];
       // click = new Click();

    }


    /**
     * Disegna le parti grafiche che verranno visualizzate nel manù e si occupa di controllare se clicchi sopra alcune di queste, ovvero i bottoni
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Drawer.drawMainMenu(game, menu, playButton, exitButton, multiplayerofflineButton, score, multiplayeronlineButton, playbutton, onlinebutton, offlinebutton, scorebutton, exitbutton);


        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWight / 2) - (score.getWidth() * coeffDimensionale) / 2 && (Gdx.input.getX() < newWight / 2 + (score.getWidth() * coeffDimensionale) / 2) && (newHeight - Gdx.input.getY() > scorebutton * coeffDimensionale + barreNere) && (newHeight - Gdx.input.getY() < scorebutton * coeffDimensionale + score.getHeight() * coeffDimensionale + barreNere)) {
                screenHandler.gestisciMenu(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (exitButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > exitbutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < exitbutton * coeffDimensionale + exitButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.exit();
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > playbutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < playbutton * coeffDimensionale + exitButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.singlePlayer(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > onlinebutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < onlinebutton * coeffDimensionale + exitButton.getHeight() * coeffDimensionale + barreNere))) {

                screenHandler.multiplayerOffline(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > offlinebutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < offlinebutton * coeffDimensionale + exitButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.multiplayerOnline(game);
            }
        }
        game.getBatch().end();
    }

    /**
     * si occupa di ridimensionare la finestra
     * @param width larghezza della finestra
     * @param height altezza della finestra
     */

    @Override
    public void resize(int width, int height) {
        this.newHeight = height;
        this.newWight = width;

        tempVet = resizer.toResize(height, width);
        barreNere = tempVet[0];
        coeffDimensionale = tempVet[1];

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
