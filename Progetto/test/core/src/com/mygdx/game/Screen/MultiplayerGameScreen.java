package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.ClientServer.ClientThread;
import com.mygdx.game.hud.Hud;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;
import sprites.Paddle;
import sprites.powerup.*;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

//Da implementare i pattern

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


    public MultiplayerGameScreen(BreakGame game,InetAddress address, String playerName) {
        palla = new Ball();
        paddles = new ArrayList<Paddle>();
        bricks = new ArrayList<AbstractBrick>();
        powerUps = new ArrayList<PowerUp>();
        playerNames = new ArrayList<String>();
        scores = new ArrayList<String>();
        lives = new ArrayList<String>();
        error = false;
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

        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        if(error){
            game.setScreen(new MainMenuScreen(game));
        }
        String m = thread.getMessage();
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

        game.getBatch().end();

        hud = new Hud(game.getBatch(), playerNames, scores, lives);
        hud.stage.draw();

        game.getBatch().begin();

        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.brickresize, brick.getHeight() * Info.brickresize);
        }

        for (PowerUp p : powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth() * Info.powerUpResize, p.getHeight() * Info.powerUpResize);
        }
        if (numeroPlayer > 0) {
            for (int i = 0; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.paddleresizex.get(i), paddles.get(i).getHeight() * Info.paddleresize);
            }
        }
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);
        game.getBatch().end();

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

    public void parseMessage(String message) { // si potrebbe implementare pure fabrication e fare un altra classe, che sarebbe anche HightCOesion
        int i;
        if (message.length() < 10) {
            thread.interrupt();
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
                    Info.paddleresizex.add(0.5f);
                }

                for (Paddle paddle : paddles) {
                    paddle.getPosition().x = Float.parseFloat(lines[1].split(" ")[paddles.indexOf(paddle)]);
                    Info.paddleresizex.set(paddles.indexOf(paddle), Float.parseFloat(lines[2].split(" ")[paddles.indexOf(paddle)]));
                }

                playerNames.removeAll(playerNames);
                lives.removeAll(lives);
                scores.removeAll(scores);

                for (i = 3; i < numeroPlayer + 3; i++) {
                    playerNames.add(lines[i].split(" ")[0]);
                    scores.add(lines[i].split(" ")[1]);
                    lives.add(lines[i].split(" ")[2]);

                }


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

                bg = new Texture(lines[i]);
            }
        }
    }

    public void keyPressed(int key) {
        String s = "" + key;
        byte[] b = s.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, address, serverPort);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
        }
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
