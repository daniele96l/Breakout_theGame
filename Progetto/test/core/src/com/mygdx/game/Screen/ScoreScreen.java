package com.mygdx.game.Screen;

import DatabaseManagement.Database;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Leaderboard.Score;
import help.GameState;
import help.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @Author Regna, Scillaci, Ligato
 */

public class ScoreScreen implements Screen {
//Applicato HightCoesion

    private Texture menu, backButton;
    BitmapFont bitmapFont;
    private ArrayList<Score> scores;
    private Score score;
    private GameState gameState;
    private Texture scoreScreen;
    private float newHeight, newWight, coeffDimensionale = 1;
    private static boolean drawn;
    private Database db = new Database();
    float barreNere = 0;
    int backbuttonx = 500;
    int backbuttony = 50;
    private float tempVet[];
    private BreakGame game;
    private Resizer resizer;

    /**
     *  Salva il parametro game
     * @param game ???
     */
    public ScoreScreen(BreakGame game) {
        this.game = game;
        tempVet = new float[2];
        resizer = new Resizer();
    }

    /**
     * Si occupa di salvare nei parametri i valori telle texture, Gli array list e impostare i Bitmap
     */

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        scores = new ArrayList<Score>();
        scoreScreen = new Texture("menuscreen.jpg");
    }

    /**
     *
     *  Si occupa di renderizzare la finestra, quindi mostrate tutte le texture, e controllare se il bottone sia cliccato
     * @param delta frequenza di aggiornamento frame
     */

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backButton = new Texture("menu.png");

        game.getBatch().draw(scoreScreen, 0, 0);
        game.getBatch().draw(backButton, backbuttonx, backbuttony);
        bestScores(game.getBatch());

        if (Gdx.input.getX() > (newWight / 2) - (backButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (backButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > backbuttony * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < backbuttony * coeffDimensionale + backButton.getHeight() * coeffDimensionale + barreNere))) {
            if (Gdx.input.justTouched()) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
        game.getBatch().end();
    }


    /**
     * Si occupa di ridimensionare la finestra
     *
     * @param width larghezza finestra
     * @param height altezza finestra
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

    /**
     * Inizia a disegnare i punteggi partenzo dall'altezza y
     * @param batch
     */
    public void bestScores(SpriteBatch batch) {
        bitmapFont.draw(batch, db.printTableOff(), 500, 704);

    }
}
