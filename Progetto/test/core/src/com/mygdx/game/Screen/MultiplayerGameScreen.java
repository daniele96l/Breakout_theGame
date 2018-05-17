package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.ClientServer.ClientThread;
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


public class MultiplayerGameScreen implements Screen {

    private BreakGame game;
    private Ball palla;
    private int numeroPlayer;
    private ArrayList<Paddle> paddles;
    private ArrayList<AbstractBrick> bricks;
    private ArrayList<PowerUp> powerUps;
    private ClientThread thread;
    private int key;
    private DatagramSocket datagramSocket;
    private int serverPort;
    private InetAddress address;


    public MultiplayerGameScreen(BreakGame game) {
        palla=new Ball();
        paddles=new ArrayList<Paddle>();
        bricks=new ArrayList<AbstractBrick>();
        powerUps=new ArrayList<PowerUp>();
        boolean f=true;
        try {
            address=InetAddress.getByName(JOptionPane.showInputDialog(null, "Insert IP address", "IP", 1));
            byte[] b=new byte[1024];
            datagramSocket=new DatagramSocket();
            DatagramPacket packet=new DatagramPacket(b, b.length, address, 4444);
            datagramSocket.send(packet);
            DatagramPacket packet1=new DatagramPacket(b, b.length);
            datagramSocket.receive(packet1);
            serverPort=Integer.parseInt(new String(packet1.getData(),0,packet1.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread=new ClientThread(address, serverPort, datagramSocket);
        thread.start();

        this.game=game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(new Texture("bg.jpg"), 0, 0);
        String m=thread.getMessage();
        if(!m.equals(""))
        {
            parseMessage(m);
        }
        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.brickresize, brick.getHeight() * Info.brickresize);
        }

        for(PowerUp p:powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth()*Info.powerUpResize, p.getHeight()*Info.powerUpResize);
        }
        if(numeroPlayer>0) {
            for (int i = 0; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.paddleresizex.get(i), paddles.get(i).getHeight() * Info.paddleresize);
            }
        }
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);
        game.getBatch().end();


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            keyPressed(Input.Keys.LEFT);
        }
        else {
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                keyPressed(Input.Keys.RIGHT);
            }
            else {
                keyPressed(0);
            }
        }
    }

    public void parseMessage(String message) {
        int i;
        String[] lines=message.split("\t"); //Separa le righe

        palla.getPositionBall().x=Float.parseFloat(lines[0].split(" ")[0]);
        palla.getPositionBall().y=Float.parseFloat(lines[0].split(" ")[1]);

        numeroPlayer=lines[1].split(" ").length;
        paddles.removeAll(paddles);
        for(int j=0; j<numeroPlayer; j++) {
            paddles.add(new Paddle(numeroPlayer, j+1));
            Info.paddleresizex.add(0.5f);
        }

        for(Paddle paddle:paddles) {
            paddle.getPosition().x = Float.parseFloat(lines[1].split(" ")[paddles.indexOf(paddle)]);
            Info.paddleresizex.set(paddles.indexOf(paddle), Float.parseFloat(lines[2].split(" ")[paddles.indexOf(paddle)]));
        }

        bricks.removeAll(bricks);

        for(i=3; i<lines.length; i++) {
            if(lines[i].split(" ")[2].equals("Brick") || lines[i].split(" ")[2].equals("Brick\n")  ) {
                bricks.add(new Brick((int)Float.parseFloat(lines[i].split(" ")[0]),(int)Float.parseFloat(lines[i].split(" ")[1])));
            }
            else {
                if (lines[i].split(" ")[2].equals("HardBrick") || lines[i].split(" ")[2].equals("HardBrick\n")) {
                    bricks.add(new HardBrick((int)Float.parseFloat(lines[i].split(" ")[0]), (int)Float.parseFloat(lines[i].split(" ")[1])));
                }
                else
                    break;
            }

        }

        powerUps.removeAll(powerUps);

        while(i<lines.length) {
            if(lines[i].split(" ")[2].equals("ExtraLife")){
                powerUps.add(new ExtraLife((int)Float.parseFloat(lines[i].split(" ")[0]), (int)Float.parseFloat(lines[i].split(" ")[1])));
            }
            if(lines[i].split(" ")[2].equals("LossLife")) {
                powerUps.add(new LossLife((int)Float.parseFloat(lines[i].split(" ")[0]), (int)Float.parseFloat(lines[i].split(" ")[1])));

            }
            if(lines[i].split(" ")[2].equals("LongPaddle")) {
                powerUps.add(new LongPaddle((int)Float.parseFloat(lines[i].split(" ")[0]), (int)Float.parseFloat(lines[i].split(" ")[1])));

            }
            if(lines[i].split(" ")[2].equals("ShortPaddle")) {
                powerUps.add(new ShortPaddle((int)Float.parseFloat(lines[i].split(" ")[0]), (int)Float.parseFloat(lines[i].split(" ")[1])));

            }
            i++;
        }
    }

    public void keyPressed(int key) {
       String s=""+key;
       byte[] b=s.getBytes();
       DatagramPacket packet=new DatagramPacket(b, b.length, address, serverPort);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
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
