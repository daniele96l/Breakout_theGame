package com.mygdx.game.ClientServer;

import DatabaseManagement.Database;
import DatabaseManagement.Enum.DropType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Collision;
import com.mygdx.game.CommandPlayer;
import com.mygdx.game.Leaderboard.Score;
import com.mygdx.game.Levels.GestoreLivelli;
import com.mygdx.game.Player.HumanPlayer;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Player.RobotPlayer;
import com.mygdx.game.State.WinLoseState;
import com.mygdx.game.hud.Hud;
import help.GameState;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;
import sprites.Paddle;
import sprites.powerup.*;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Server extends Game {
    private DatagramSocket datagramSocket;
    private int portaServer = 4444;
    private ArrayList<ServerThreadIn> threadsIn;
    private ArrayList<DatagramSocket> sockets;
    private ArrayList<Player> players;
    private ArrayList<Paddle> paddles;
    private ArrayList<CommandPlayer> commandPlayers;
    private int numeroPlayer = 1;
    private Collision collision;
    private ArrayList<AbstractBrick> bricks = new ArrayList();
    private Ball palla;
    int contatore;
    private Texture bg;
    private GameState gameState;
    private int contatore2 = 0;
    private boolean nextLevel;
    private ArrayList<Integer> indici;
    private ArrayList<PowerUp> powerUps;
    private int matEliminati;
    private GestoreLivelli gestoreLivelli;
    private int livelloCorrente;
    private boolean isFinished;
    boolean primo, secondo;
    static private int myScore;
    static private boolean nick;
    private Score score;
    private int newHeight, newWight;
    private static String playerName;
    private WinLoseState winLoseState;
    private boolean pause;
    private Hud hud;
    private Player gameHolder;  //Giocatore che ha toccato la pallina per ultimo
    private int brickCounter;
    private int tmpDT;
    private boolean isPaused;
    private boolean isFirstCalled;
    private boolean creato;
    private Date datetmp;
    private ArrayList<Date> date;
    private Database db = new Database();


    @Override
    public void create() {
        numeroPlayer = (Integer.parseInt(JOptionPane.showInputDialog(null, "Number of player", "Enter the number of player ", 1)));
        initServer();
        creato = false;
        players = new ArrayList<Player>();
        paddles = new ArrayList<Paddle>();
        date = new ArrayList<Date>();
        commandPlayers = new ArrayList<CommandPlayer>();

        for (int i = 0; i < numeroPlayer; i++) {
            players.add(new HumanPlayer("player " + i + 1));
            paddles.add(new Paddle(numeroPlayer, i + 1));
            Info.paddleresizex.add(0.5f);
            commandPlayers.add(new CommandPlayer(paddles.get(i), players.get(i), numeroPlayer, i + 1));
        }

        isFirstCalled = true;

        if (isFirstCalled) {
            isFirstCalled = false;
            isFinished = false;
            livelloCorrente = 1;
            nextLevel = false;
            isPaused = false;
            palla = new Ball();
            tmpDT = Info.dt;

            for (int i = 0; i < numeroPlayer; i++) {
                date.add(new Date());
            }

            updateScene();
            updateLevel();
            gameHolder = players.get(0);
            brickCounter = 0;
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }
    }

    public void render() {
        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();

        }
        palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y * Info.dt);
        palla.getBoundsBall().setPosition(palla.getPositionBall());
        ArrayList<PowerUp> tmpPUps = new ArrayList<PowerUp>();
        for (PowerUp p : powerUps) {
            if (p.getPosition().y + Info.powerUpHeight < 0) {
                tmpPUps.add(p);
            }
            p.getPosition().add(p.getSpeed().x * Info.dt, p.getSpeed().y * Info.dt);
            p.getBounds().setPosition(p.getPosition());
        }
        for (PowerUp p : tmpPUps) {
            powerUps.remove(p);
        }

        for (int i = 0; i < commandPlayers.size(); i++) {
            commandPlayers.get(i).move(threadsIn.get(i).getKey());//mipermettedimuovereilgiocatore
        }

        writeMessage();

        gestisciCollisioni();

        checktimerpowerup(); // controlla il tempo


        if (matEliminati == gestoreLivelli.getLivello(livelloCorrente - 1).getnMatMorbidi()) {
            gameState = GameState.YOU_WON;

            livelloCorrente++;
            if (livelloCorrente > gestoreLivelli.getNumeroLivelli()) {
                isFinished = true;
            }
        }

        if (palla.getPositionBall().y <= 0) {
            lostLife(palla.getPositionBall().x, false);
            updateScene();
        }


        if (gameState == GameState.YOU_WON) {
            if (isFinished) {
                livelloCorrente = 1;
                isFinished = false;
                db.modify(ranGen(), playerName, players.get(0).getScore(), DropType.INSERT);
                updateScene();
                updateLevel(); /////////////SERVE UNO SCREEN DI FINE GIOCO
            } else {
                nextLevel = true;
                updateScene();
                updateLevel();
            }
        }
        if (gameState == GameState.GAME_OVER) {

        }

        if (gameState == GameState.WAIT) {
            Info.dt = 0;
            if (!creato) {
                datetmp = new Date();
                creato = true;
            }
            if (checktimer(3000, datetmp)) {
                Info.dt = tmpDT;
                gameState = GameState.ACTION;
                creato = false;
            }
        }
    }

    private void writeMessage() {
        String message = "";
        message += palla.getPositionBall().x + " " + palla.getPositionBall().y + "\t";
        for (Paddle paddle : paddles) {
            message += paddle.getPosition().x + " ";
        }
        message += "\t";
        for (float f : Info.paddleresizex) {
            message += f + " ";
        }
        message += "\t";
        for (AbstractBrick brick : bricks) {
            message += brick.getPositionBrick().x + " " + brick.getPositionBrick().y + " ";
            if (brick instanceof HardBrick) {
                message += "HardBrick\t";
            } else {
                message += "Brick\t";
            }
        }
        for (PowerUp powerUp : powerUps) {
            message += powerUp.getPosition().x + " " + powerUp.getPosition().y + " ";
            if (powerUp instanceof ExtraLife) {
                message += "ExtraLife\t";
            }
            if (powerUp instanceof LossLife) {
                message += "LossLife\t";
            }
            if (powerUp instanceof LongPaddle) {
                message += "LongPaddle\t";
            }
            if (powerUp instanceof ShortPaddle) {
                message += "ShortPaddle\t";
            }
        }

        String bgPath = ((FileTextureData) bg.getTextureData()).getFileHandle().name();

        message += bgPath + "\t";

        message = message.substring(0, message.length() - 1);
        byte[] bytes = message.getBytes();

        for (ServerThreadIn thread : threadsIn) {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, thread.getAddress(), thread.getPort());
            try {
                thread.getSocket().send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void updateScene() {
        palla.setDefaultState();
        for (Paddle paddle : paddles) {
            paddle.setDefaultState(numeroPlayer);
        }
        commandPlayers = new ArrayList<CommandPlayer>();
        for (int i = 0; i < players.size(); i++) {
            commandPlayers.add(new CommandPlayer(paddles.get(i), players.get(i), numeroPlayer, i + 1));
        }
    }

    private void updateLevel() {
        gestoreLivelli = new GestoreLivelli("fileLivelli.txt");
        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        powerUps = new ArrayList<PowerUp>();
        bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        matEliminati = 0;
        gameState = GameState.WAIT;
    }

    private String ranGen() {
        Random n = new Random();
        String s = "" + n.nextInt(1000);
        return s;
    }

    private void checktimerpowerup() {
        if (date != null) {
            Date date2 = new Date();
            for (int i = 0; i < numeroPlayer; i++)
                if (date2.getTime() - date.get(i).getTime() > Info.durataPowerUp)
                    Info.paddleresizex.set(i, Info.paddleresize);
        }
    }

    private boolean checktimer(int durata, Date datetmp) {
            Date date2 = new Date();
            if (date2.getTime() - datetmp.getTime() > durata) {
                return true;
            }
        return false;
    }


    private void initServer() {
        try {
            datagramSocket = new DatagramSocket(portaServer);
            threadsIn = new ArrayList<ServerThreadIn>();
            sockets = new ArrayList<DatagramSocket>();
            while (threadsIn.size() < numeroPlayer) {
                byte[] b = new byte[1024];
                DatagramPacket packet = new DatagramPacket(b, b.length);
                datagramSocket.receive(packet);
                int newPort = portaServer + threadsIn.size() + 1;
                b = ((Integer) newPort).toString().getBytes();
                DatagramPacket packetBack = new DatagramPacket(b, b.length, packet.getAddress(), packet.getPort());
                datagramSocket.send(packetBack);
                sockets.add(new DatagramSocket(newPort));
                threadsIn.add(new ServerThreadIn(sockets.get(sockets.size() - 1), packet.getAddress(), packet.getPort()));
            }

            datagramSocket.close();

            for (ServerThreadIn t : threadsIn) {
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gestisciCollisioni() {
        float oldSpeedBallX = palla.getSpeedBall().x;
        float oldSpeedBallY = palla.getSpeedBall().y;

        collision = new Collision(palla);

        indici = new ArrayList<Integer>();
        for (AbstractBrick brick : bricks) {
            if (collision.check(brick)) {
                indici.add(bricks.indexOf(brick));
            }
        }

        collision.checkBorderCollision();

        for (int i = 0; i < numeroPlayer; i++) {
            if (collision.checkSide(paddles.get(i))) {
                gameHolder = players.get(i);
                brickCounter = 0;
            }
            ArrayList<PowerUp> tempPowerUps = new ArrayList<PowerUp>();
            for (PowerUp p : powerUps) {
                if (collision.checkPowerUp(paddles.get(i), p)) {
                    tempPowerUps.add(p);
                    p.effect(players.get(paddles.indexOf(paddles.get(i))), paddles.get(i), palla);
                    lostLife(palla.getPositionBall().x, true);
                    if (Info.paddleresizex.get(i) != Info.paddleresize) { // qua verifico che sia stato cambiato la resize una volta che prendo il powerup
                        date.set(i, new Date());
                    }

                }


            }
            for (PowerUp p : tempPowerUps) {
                powerUps.remove(p);
            }
        }

        if (!indici.isEmpty()) {
            if (indici.size() >= 2) {
                contatore = 0;
                contatore2 = 0;
                ArrayList<AbstractBrick> tempMatt = new ArrayList<sprites.Brick.AbstractBrick>();
                for (int i : indici) {
                    if (bricks.get(i).getPositionBrick().x < palla.getPositionBall().x) {
                        contatore++;
                    }
                    if (bricks.get(i).getPositionBrick().x > palla.getPositionBall().x) {
                        contatore2++;
                    }
                    tempMatt.add(bricks.get(i));

                }

                if (contatore == 1 && contatore2 == 1) /////////////NUOVO
                    palla.setSpeedBall(new Vector2(oldSpeedBallX, -oldSpeedBallY));
                else /////////////NUOVO
                    palla.setSpeedBall(new Vector2(-oldSpeedBallX, oldSpeedBallY)); /////////////NUOVO


                for (AbstractBrick brick : tempMatt) {
                    if (brick instanceof Brick) {
                        if (brick.hasPowerUp()) {
                            powerUps.add(brick.getPowerUp());
                        }
                        bricks.remove(brick);
                        matEliminati++;
                        players.get(players.indexOf(gameHolder)).setScore(gameHolder.getScore() + (int) Math.pow(2, brickCounter));
                        brickCounter++;
                    }
                }
            } else {
                if (bricks.get(indici.get(0)) instanceof Brick) {
                    if (bricks.get(indici.get(0)).hasPowerUp()) {
                        powerUps.add(bricks.get(indici.get(0)).getPowerUp());
                    }
                    bricks.remove((int) indici.get(0));
                    matEliminati++;
                    players.get(players.indexOf(gameHolder)).setScore(gameHolder.getScore() + (int) Math.pow(2, brickCounter));
                    brickCounter++;
                }
            }
        }
    }

    private void lostLife(float positionX, boolean powerup) {
        int range = Info.larghezza / numeroPlayer;
        Player loser = new RobotPlayer("default", palla, paddles.get(0));

        for (int i = 0; i < numeroPlayer; i++) {
            if (positionX >= i * range && positionX < (i + 1) * range) {
                loser = players.get(i);
            }
        }
        if (!powerup) {
            loser.setLives(loser.getLives() - 1);
            gameState = GameState.WAIT;
        }

        if (loser.getLives() < 0) {
            if (players.get(0).equals(loser)) {
                db.modify(ranGen(), playerName, players.get(0).getScore(), DropType.INSERT);
                gameState = GameState.GAME_OVER;
            } else {
                int index = players.indexOf(loser);
                players.remove(loser);
                paddles.remove(index);
                numeroPlayer--;
            }
        }
    }


}
