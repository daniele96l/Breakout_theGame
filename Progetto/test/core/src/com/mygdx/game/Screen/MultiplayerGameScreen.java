package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.mygdx.game.ClientServer.ClientThread;
import com.mygdx.game.hud.Hud;
import com.mygdx.game.logic.Resizer;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;
import sprites.Paddle;
import sprites.powerup.*;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.GenericDeclaration;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

/*
* DA FARE:
*
* Polimorfismo nella parsemessage()
*
*
*
* */

/**
 * @author Regna, Schillaci
 *
 * La classe si occupa di gestire la schermata della modalita di gioco "multiplayer online"
 *
 */
public class MultiplayerGameScreen implements Screen {

    private BreakGame game;
    private Ball palla;
    private int numeroPlayer;
    private ArrayList<Paddle> paddles;
    private ArrayList<AbstractBrick> bricks;
    private ArrayList<PowerUp> powerUps;
    private Texture bg;
    private ClientThread thread;
    private int key;
    private DatagramSocket datagramSocket;
    private int serverPort;
    private InetAddress address;
    private String playerName;
    private Hud hud;
    private ArrayList<String> playerNames;
    private ArrayList<String> scores;
    private ArrayList<String> lives;
    private boolean error;
    private int newHeight;
    private int newWight;
    private Resizer resizer;
    private Music musicGame;


    public MultiplayerGameScreen(BreakGame game, InetAddress address, String playerName) {
        this.playerName = playerName;
        this.game = game;
        palla = new Ball();
        paddles = new ArrayList<Paddle>();
        bricks = new ArrayList<AbstractBrick>();
        powerUps = new ArrayList<PowerUp>();
        playerNames = new ArrayList<String>();
        scores = new ArrayList<String>();
        lives = new ArrayList<String>();
        error = false;
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        try {
            byte[] b = playerName.getBytes();
            datagramSocket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(b, b.length, address, 4444);
            datagramSocket.send(packet);
            b = new byte[1024];
            DatagramPacket packet1 = new DatagramPacket(b, b.length);
            datagramSocket.receive(packet1);
            serverPort = Integer.parseInt(new String(packet1.getData(), 0, packet1.getLength()));
        } catch (Exception e) {
            error = true;
            JOptionPane.showMessageDialog(null, "Nerwork Error", "Network error", 1);
        }

        thread = new ClientThread(address, serverPort, datagramSocket);
        thread.start();
        resizer = new Resizer();
    }

    @Override
    public void show() {

    }

    /**
     *
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     *
     * disegna le parti grafiche dello scenario 60 volte al secondo facendo un controllo
     * sul messaggio che gli viene passato dal server
     */
    @Override
    public void render(float delta) {
        musicGame.play();
        game.getBatch().begin();
        if (error) {
            game.setScreen(new MainMenuScreen(game));
        }
        String m = thread.getMessage();
        controlMessage(m);
        game.getBatch().end();
        hud = new Hud(game.getBatch(), playerNames, scores, lives);
        hud.stage.draw();
        Drawer.drawMultiplayer(bricks, game, powerUps, numeroPlayer, paddles, palla);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            keyPressed(Input.Keys.LEFT);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                keyPressed(Input.Keys.RIGHT);
            } else {
                keyPressed(0);
            }
        }
    }

    /**
     *
     * @param message è il messaggio che arriva dal server
     *
     * il metodo permette di estrarre dal messaggio le informazioni necessarie per assegnare un valore alle variabili
     * che rappresentano gli elementi dello scenario che vengono disegnati.
     *
     */

    public void parseMessage(String message) { // si potrebbe implementare pure_fabrication e fare un altra classe, che sarebbe anche HightCOesion
        int i;
        if (message.length() < 10) {
            thread.interrupt();
            musicGame.stop();
            game.setScreen(new LoseGameScreen(game));
        } else {
            String[] lines = message.split("\t"); //Separa le righe

            palla.getPositionBall().x = Float.parseFloat(lines[0].split(" ")[0]);
            palla.getPositionBall().y = Float.parseFloat(lines[0].split(" ")[1]);

            numeroPlayer = lines[1].split(" ").length;
            paddles.removeAll(paddles);
            if (numeroPlayer > 0) {
                for (int j = 0; j < numeroPlayer; j++) {
                    paddles.add(new Paddle(numeroPlayer, j + 1));
                    Info.getInstance().getPaddleresizex().add(0.5f);
                }

                for (Paddle paddle : paddles) {
                    paddle.getPosition().x = Float.parseFloat(lines[1].split(" ")[paddles.indexOf(paddle)]);
                    Info.getInstance().getPaddleresizex().set(paddles.indexOf(paddle), Float.parseFloat(lines[2].split(" ")[paddles.indexOf(paddle)]));
                }

                playerNames.removeAll(playerNames);
                lives.removeAll(lives);
                scores.removeAll(scores);

                for (i = 3; i < numeroPlayer + 3; i++) {
                    playerNames.add(lines[i].split(" ")[0]);
                    scores.add(lines[i].split(" ")[1]);
                    lives.add(lines[i].split(" ")[2]);

                }


                int numeroBricks = bricks.size();
                bricks.removeAll(bricks);

                while (i < lines.length - 1) {
                    if (lines[i].split(" ")[2].equals("Brick") || lines[i].split(" ")[2].equals("Brick\n")) {
                        bricks.add(new Brick((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));
                        i++;
                    } else {
                        if (lines[i].split(" ")[2].equals("HardBrick") || lines[i].split(" ")[2].equals("HardBrick\n")) {
                            bricks.add(new HardBrick((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));
                            i++;
                        } else
                            break;
                    }
                }

                if (bricks.size() < numeroBricks) {
                    Gdx.audio.newMusic(Gdx.files.internal("audio.mp3")).play();
                }

                ArrayList<PowerUp> tmpPowerUp = (ArrayList<PowerUp>) powerUps.clone();

                System.out.println(powerUps);
                System.out.println(tmpPowerUp);
                powerUps.removeAll(powerUps);

                while (i < lines.length - 1) {
                    if (lines[i].split(" ")[2].equals("ExtraLife")) {
                        powerUps.add(new ExtraLife((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));
                    }
                    if (lines[i].split(" ")[2].equals("LossLife")) {
                        powerUps.add(new LossLife((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));

                    }
                    if (lines[i].split(" ")[2].equals("LongPaddle")) {
                        powerUps.add(new LongPaddle((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));

                    }
                    if (lines[i].split(" ")[2].equals("ShortPaddle")) {
                        powerUps.add(new ShortPaddle((int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));

                    }

                    i++;
                }

                if (tmpPowerUp.size() > powerUps.size()) {
                    if (powerUps.size() == 0) {
                        if (!(tmpPowerUp.get(0).getPosition().y < 0)) {
                            Gdx.audio.newMusic(Gdx.files.internal("good.mp3")).play();
                        }
                    } else {
                        if (!(tmpPowerUp.get(0).getPosition().y < 0)) {
                            Gdx.audio.newMusic(Gdx.files.internal("good.mp3")).play();
                        }
                    }
                }


                bg = new Texture(lines[i]);
                i++;


            }
        }
    }


    public void controlMessage(String m) {
        if (!m.equals("")) {
            parseMessage(m);

            boolean found = false;
            for (String name : playerNames) {
                if (name.equals(playerName)) {
                    found = true;
                }
            }
            if (!found) {
                game.setScreen(new LoseGameScreen(game));
            }


            game.getBatch().draw(bg, 0, 0);
        }
    }

    /**
     *
     * @param key è il valore che rappresenta il tasto premuto dal giocatore per spostare il suo paddle
     *
     * il metodo si occupa di costruire e inviare il datagramma al server
     */
    public void keyPressed(int key) {
        String s = "" + key;
        byte[] b = s.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, address, serverPort);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
        }
    }

    /**
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     *
     * si occupa di ridimensionare la finestra
     */

    @Override
    public void resize(int width, int height) {
        this.newHeight = height;
        this.newWight = width;


        resizer.toResize(height, width);

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