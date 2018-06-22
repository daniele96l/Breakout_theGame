package com.mygdx.game.help;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Il costruttore di questa classe contiene le variabili necessarie
 * al corretto funzionamento del gioco
 *
 * @author Cristian Regna, Alberto Schillaci, Daniele Ligato
 */
public class Info {
    private static Info instance;
    private int altezza;
    private int larghezza;
    private int velBall;
    private int velPaddle;
    private int dt;
    private ArrayList<Float> paddleresizex;
    private float paddleresize;
    private float brickresize;
    private float ballresize;
    private int brickGapX;
    private int brickGapY;
    private int defaultLivesNum;
    private int hudHeight;
    private int powerUpSpeed;
    private float powerUpResize;
    private float powerUpChance; //Probabilità da 1 a 10 che in un mattoncino ci sia un power up
    private int numeroPowerUp; //Numero di power up esistenti (da aggiornare se se ne crea uno nuovo)
    private int powerUpWidth;
    private int powerUpHeight;
    private int durataPowerUp;

    private Info() {
        altezza = 720;
        larghezza = 1280;
        velBall = 7;
        velPaddle = 10;
        dt = 1;
        paddleresizex = new ArrayList<Float>();
        paddleresize = 0.5f;
        brickresize = 0.5f;
        ballresize = 0.8f;
        brickGapX = 5;
        brickGapY = 3;
        defaultLivesNum = 3;
        hudHeight = 80;
        powerUpSpeed = 3;
        powerUpResize = 0.5f;
        powerUpChance = 2; //Probabilità da 1 a 10 che in un mattoncino ci sia un power up
        numeroPowerUp = 4; //Numero di power up esistenti (da aggiornare se se ne crea uno nuovo)
        powerUpWidth = 50;
        powerUpHeight = 45;
        durataPowerUp = 10000;
    }

    /**
     * Metodo di implementazione del pattern Singleton
     *
     * @return istanza di info
     */

    public static synchronized Info getInstance() {
        if (instance == null) {
            instance = new Info();
        }
        return instance;
    }


    /**
     * Questo metodo restituisce la velocità del paddle di gioco
     *
     * @return velocità del paddle
     */
    public int getVelPaddle() {
        return velPaddle;
    }


    /**
     * Questo metodo restituisce la larghezza del mattonicino in funzione della texture
     *
     * @return la larghezza del mattoncino in funzione della texture
     */
    public int getBrickWidth() {
        Texture brick = new Texture("normalBrick.jpg");
        return (int) (brick.getWidth() * brickresize);
    }

    /**
     * Questo metodo restituisce la altezza del mattonicino in funzione della texture
     *
     * @return l altezza del mattoncino in funzione della texture
     */
    public int getBrickHeight() {
        Texture brick = new Texture("normalBrick.jpg");
        return (int) (brick.getHeight() * brickresize);
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getAltezza() {
        return altezza;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public int getVelBall() {
        return velBall;
    }

    public int getDt() {
        return dt;
    }

    public ArrayList<Float> getPaddleresizex() {
        return paddleresizex;
    }

    public float getPaddleresize() {
        return paddleresize;
    }

    public float getBrickresize() {
        return brickresize;
    }

    public float getBallresize() {
        return ballresize;
    }

    public int getBrickGapX() {
        return brickGapX;
    }

    public int getBrickGapY() {
        return brickGapY;
    }

    public int getDefaultLivesNum() {
        return defaultLivesNum;
    }

    public int getHudHeight() {
        return hudHeight;
    }

    public int getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public float getPowerUpResize() {
        return powerUpResize;
    }

    public float getPowerUpChance() {
        return powerUpChance;
    }

    public int getNumeroPowerUp() {
        return numeroPowerUp;
    }

    public int getPowerUpWidth() {
        return powerUpWidth;
    }

    public int getPowerUpHeight() {
        return powerUpHeight;
    }

    public int getDurataPowerUp() {
        return durataPowerUp;
    }
}
