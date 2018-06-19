package com.mygdx.game.graphics.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.clientserver.ClientThread;
import com.mygdx.game.graphics.Drawer;
import com.mygdx.game.graphics.Hud;
import com.mygdx.game.logic.Resizer;
import com.mygdx.game.eccezioni.IllegalBrick;
import com.mygdx.game.eccezioni.IllegalPowerUp;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Brick.Brick;
import com.mygdx.game.graphics.sprites.Paddle;
import com.mygdx.game.graphics.sprites.SpriteFactory;
import com.mygdx.game.graphics.sprites.powerup.*;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/*
* DA FARE:
*
* Polimorfismo nella parsemessage()
*
*
*
* */

/**
 * La classe si occupa di gestire la schermata della modalita di gioco "multiplayer online"
 *
 * @author Regna, Schillaci
 */
public class MultiplayerGameScreen implements Screen {

    private BreakGame game;
    private Ball palla;
    private int numeroPlayer;
    private ArrayList<Paddle> paddles;
    private ArrayList<Brick> bricks;
    private ArrayList<PowerUp> powerUps;
    private Texture bg;
    private ClientThread thread;
    private DatagramSocket datagramSocket;
    private int serverPort;
    private InetAddress address;
    private String playerName;
    private ArrayList<String> playerNames;
    private ArrayList<String> scores;
    private ArrayList<String> lives;
    private Resizer resizer;
    private Music musicGame;


    public MultiplayerGameScreen(BreakGame game, InetAddress address, String playerName) {
        this.address=address;
        this.playerName = playerName;
        this.game = game;
        palla = new Ball();
        paddles = new ArrayList<Paddle>();
        bricks = new ArrayList<Brick>();
        powerUps = new ArrayList<PowerUp>();
        playerNames = new ArrayList<String>();
        scores = new ArrayList<String>();
        lives = new ArrayList<String>();
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
            JOptionPane.showMessageDialog(null, "Nerwork Error", "Network error", 1);
            game.setScreen(new MainMenuScreen(game));
        }

        thread = new ClientThread(address, serverPort, datagramSocket);
        thread.start();
        resizer = new Resizer();
    }

    @Override
    public void show() {

    }

    /**
     * disegna le parti grafiche dello scenario 60 volte al secondo facendo un controllo
     * sul messaggio che gli viene passato dal server
     *
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     */
    @Override
    public void render(float delta) {
        game.getBatch().begin();
        musicGame.play();
        String m = thread.getMessage();
        controlMessage(m);
        game.getBatch().end();
        Hud hud = new Hud(game.getBatch(), playerNames, scores, lives);
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
     * il metodo permette di estrarre dal messaggio le informazioni necessarie per assegnare un valore alle variabili
     * che rappresentano gli elementi dello scenario che vengono disegnati.
     *
     * @param message è il messaggio che arriva dal server
     */

    private void parseMessage(String message) { // si potrebbe implementare pure_fabrication e fare un altra classe, che sarebbe anche HightCOesion
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
                    try {
                        bricks.add(SpriteFactory.getInstance().getBrick(lines[i].split(" ")[2], (int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));
                        i++;
                    }
                    catch (IllegalBrick exc) {
                        break;
                    }
                }

                if (bricks.size() < numeroBricks) {
                    Gdx.audio.newMusic(Gdx.files.internal("audio.mp3")).play();
                }

                ArrayList<PowerUp> tmpPowerUp = (ArrayList<PowerUp>) powerUps.clone();

                powerUps.removeAll(powerUps);

                while (i < lines.length - 1) {
                    try {
                        powerUps.add(SpriteFactory.getInstance().getPowerUp(lines[i].split(" ")[2], (int) Float.parseFloat(lines[i].split(" ")[0]), (int) Float.parseFloat(lines[i].split(" ")[1])));
                    }
                    catch(IllegalPowerUp e) {
                        System.out.println(message);
                        System.err.println(e.getMessage());
                        System.exit(-1);
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

    /**
     *
     * @param m è il messaggio
     *
     * il metodo fa un primo check sul messaggio. Se questo non è vuoto, vengono estratte le informazioni dello scenario
     * con il metodo "parseMessage".
     */
    private void controlMessage(String m) {
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
     * il metodo si occupa di costruire e inviare il datagramma al server
     *
     * @param key è il valore che rappresenta il tasto premuto dal giocatore per spostare il suo paddle
     */
    private void keyPressed(int key) {
        String s = "" + key;
        byte[] b = s.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, address, serverPort);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
        }
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
        int newHeight = height;
        int newWight = width;


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