package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;

/**
 * La classe che gestisce il memù principale con tutti i suoi bottoni
 *
 * @author Ligato, Schillaci, Regna
 */
public class MainMenuScreen implements Screen {
    private Texture menu;
    private Texture playButtonText;
    private Texture exitButtonText;
    private Texture mulOfflineButtonText;
    private Texture scoreButtonText;
    private Texture mulOnlineButtonText;
    private int newHeight, newWidth;
    BreakGame game;
    private float coeffDimensionale;
    float barreNere = 0;
    ScreenHandler screenHandler;
    private int playbutton;
    private int onlinebutton;
    private int offlinebutton;
    private int scorebutton;
    private int exitbutton;
    private Resizer resizer;
    float tempVet[];

    public MainMenuScreen(BreakGame game) {
        this.game = game;
        screenHandler = new ScreenHandler();
        playbutton = 530;
        onlinebutton=410;
        offlinebutton=290;
        scorebutton=170;
        exitbutton=50;


    }

    /**
     * Associa alle variabili le immagini che dovranno essere renderizate per essere visualizzate nella schermata
     */
    @Override
    public void show() {
        menu = new Texture("menuscreen.jpg");
        playButtonText = new Texture("play.png");
        exitButtonText = new Texture("exit.png");
        mulOfflineButtonText = new Texture("multiplayeroffline.png");
        mulOnlineButtonText = new Texture("multiplayeronline.png");
        scoreButtonText = new Texture("score.png");
        resizer = new Resizer();
        tempVet = new float[2];

    }


    /**
     * Disegna le parti grafiche che verranno visualizzate nel manù e si occupa di controllare se clicchi sopra alcune di queste, ovvero i bottoni
     *
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Drawer.drawMainMenu(game, menu, playButtonText, exitButtonText, mulOfflineButtonText, scoreButtonText, mulOnlineButtonText, playbutton, onlinebutton, offlinebutton, scorebutton, exitbutton);


        InputTouch.checkInputTouchMainMenu(newWidth, scoreButtonText,  game,  coeffDimensionale,  playButtonText,  screenHandler,  newHeight,  barreNere,  scorebutton,  exitbutton,  playbutton,  onlinebutton,  exitButtonText,  offlinebutton);
        game.getBatch().end();
    }

    /**
     * si occupa di ridimensionare la finestra
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     *
     */

    @Override
    public void resize(int width, int height) {
        this.newHeight = height;
        this.newWidth = width;

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
