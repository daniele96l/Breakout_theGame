package com.mygdx.game;

import ClientServer.ChatClient;
import ClientServer.Dato;
import DatabaseManagement.Database;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Leaderboard.Score;
import com.mygdx.game.Levels.GestoreLivelli;
import com.mygdx.game.Player.HumanPlayer;
import com.mygdx.game.Player.RobotPlayer;
import com.mygdx.game.State.MainMenuState;
import com.mygdx.game.State.PauseMenuState;
import com.mygdx.game.State.WinLoseState;
import help.GameState;
import help.Info;
import sprites.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.TextInputListener;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MyGdxGame extends Game {
    private Collision col;
    private SpriteBatch batch;
    private ArrayList<AbstractBrick> bricks = new ArrayList();
    private Ball palla;
    private Paddle paddle1;
    private Paddle paddle2;
    private boolean isMultiplayer;
    private Texture bg;
    private GameState gameState;
    private CommandPlayer commandPlayer1;
    private CommandPlayer commandPlayer2;
    private HumanPlayer player1;
    private Dato dato;
    private RobotPlayer robotPlayer;
    private boolean nextLevel;
    private ArrayList<Integer> indici;
    private int matEliminati;
    private GestoreLivelli gestoreLivelli;
    private int livelloCorrente;
    private boolean isFinished;
    private Leaderboard leaderboard;
    private MainMenuState mainMenuState;
    private PauseMenuState pauseMenuState;
    private WinLoseState winLoseState;
    static private int myScore;
    static private boolean nick;
    private Score score;
    private Database db;
    private Date data;
    private String playerName;

    Music music;
    Music music2;
    private boolean loser;
    BitmapFont bitmapFont;
    int LostLives = 0;
    String text;
    TextInputListener textInputListener;
    ChatClient chatClient = new ChatClient();


    @Override
    public void create() {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));
        music.setVolume(1);

        mainMenuState = new MainMenuState(gameState, this);
        leaderboard = new Leaderboard(gameState);
        pauseMenuState = new PauseMenuState(gameState);


        isFinished = false;
        livelloCorrente = 1;
        reset();
        bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        gameState = GameState.MENU;
        nextLevel = false;
        isMultiplayer = false;
        db = new Database();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }

        drawScene();

    }

    public void drawScene() {
        batch.begin();



        if (gameState == GameState.MENU) {
            gameState = mainMenuState.draw(batch);

            playerName = mainMenuState.getPlayerName();
        }

        if (gameState == GameState.SCORE) {
            gameState = leaderboard.draw(batch);
        }

        if (gameState == GameState.PAUSE) {
            music.pause();
            gameState = pauseMenuState.draw(batch);
            if(gameState==GameState.MENU) {
                music.stop();
            }
        }


        if (gameState == GameState.MULTIPLAYER) {
            isMultiplayer = true;
            paddle1 = new Paddle(0.5f, 2, 1);
            paddle2 = new Paddle(0.5f, 2, 2);
            robotPlayer = new RobotPlayer("giocatore", palla, paddle2); //non so cosa ci va
            commandPlayer1 = new CommandPlayer(paddle1, player1, 2, 1);
            commandPlayer2 = new CommandPlayer(paddle2, robotPlayer, 2, 2);
            gameState = GameState.ACTION;
        }

        if (gameState.equals(GameState.ACTION)) {

            music.play();
            palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y * Info.dt);
            palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);
            batch.draw(bg, 0, 0);

            for (AbstractBrick AbstractBrick : bricks) {
                batch.draw(AbstractBrick, AbstractBrick.getPositionBrick().x, AbstractBrick.getPositionBrick().y, AbstractBrick.getWidth() * Info.brickresize, AbstractBrick.getHeight() * Info.brickresize);
//disegnoimattoncini
            }


            batch.draw(paddle1, paddle1.getPosition().x, paddle1.getPosition().y, paddle1.getWidth() * Info.paddleresize, paddle1.getHeight() * Info.paddleresize);
            batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);


            bitmapFont.draw(batch, "Youlost:" + String.valueOf(LostLives) + "times", 20, 830);

            commandPlayer1.move();//mipermettedimuovereilgiocatore
            if (commandPlayer1.checkpause()) {
                gameState = GameState.PAUSE;
            }

            if (isMultiplayer) {
                batch.draw(paddle2, paddle2.getPosition().x, paddle2.getPosition().y, paddle2.getWidth() * Info.paddleresize, paddle2.getHeight() * Info.paddleresize);
                commandPlayer2.move();
            }

            gestisciCollisioni();


            if (matEliminati == gestoreLivelli.getLivello(livelloCorrente - 1).getnMatMorbidi()) {
                gameState = GameState.YOU_WON;

                livelloCorrente++;
                if (livelloCorrente > gestoreLivelli.getNumeroLivelli()) {
                    isFinished = true;
                }
            }

            if (palla.getPositionBall().y <= 0) {
                gameState = GameState.GAME_OVER;
                myScore -= matEliminati;
                LostLives++;
                music.stop();
                music2.play();
                palla.setPositionBall();
            }
        } else {

            if (gameState == GameState.YOU_WON) {
                if (isFinished) {
                    gameState = GameState.MENU;
                    livelloCorrente = 1;
                    isFinished = false;
                    reset();
                } else {
                    nextLevel = true;
                    winLoseState = new WinLoseState(batch, gameState);
                    gameState = winLoseState.draw();
                    reset();
                }
                music.stop();
            }
            if (gameState == GameState.GAME_OVER) {
                winLoseState = new WinLoseState(batch, gameState);
                gameState = winLoseState.draw();
                music.stop();
                reset();
            }
        }
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }

    public void reset() {
        palla = new Ball();
        paddle1 = new Paddle(0.5f, 1, 1);
        player1 = new HumanPlayer("Giocatore"); // non so cosa ci va
        commandPlayer1 = new CommandPlayer(paddle1, player1, 1, 1);//istanziounCommandplayer(possoavernediversiperogniplayer
        if (isMultiplayer) {
            paddle1 = new Paddle(0.5f, 2, 1);
            paddle2 = new Paddle(0.5f, 2, 2);
            robotPlayer = new RobotPlayer("giocatore", palla, paddle2); // non so cosa ci va
            commandPlayer1 = new CommandPlayer(paddle1, player1, 2, 1);
            commandPlayer2 = new CommandPlayer(paddle2, robotPlayer, 2, 2);
        }
        gestoreLivelli = new GestoreLivelli("fileLivelli.txt");
        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        matEliminati = 0;
        ///////////////////////  bg = livello.getBg();
    }

    public void gestisciCollisioni() {
        float oldSpeedBallX = palla.getSpeedBall().x;
        float oldSpeedBallY = palla.getSpeedBall().y;

        indici = new ArrayList<Integer>();
        for (AbstractBrick Abstractbrick : bricks) {
            col = new Collision(Abstractbrick, palla);
            if (col.check()) {
                indici.add(bricks.indexOf(Abstractbrick));
            }
        }

        col.checkBorderCollision();

        col.checkside(paddle1);
        if (isMultiplayer) {
            col.checkside(paddle2);
        }

        if (!indici.isEmpty()) {
            if (indici.size() >= 2) {
                ArrayList<AbstractBrick> tempMatt = new ArrayList<sprites.Brick.AbstractBrick>();
                palla.setSpeedBall(new Vector2(oldSpeedBallX, -oldSpeedBallY));
                for (int i : indici) {
                    tempMatt.add(bricks.get(i));
                }
                for (AbstractBrick brick : tempMatt) {
                    if (brick instanceof Brick) {
                        bricks.remove(brick);
                        matEliminati++;
                        myScore++;

                    }
                }
            } else {
                if (bricks.get(indici.get(0)) instanceof Brick) {//seimattoncinisonomattoncini"morbidi"lipossoeliminare
                    bricks.remove((int) indici.get(0));
                    matEliminati++;
                    myScore++;


                }
            }
        }

        ///////////////////////////////VARIABILI CHE ANDRANNO NEL DATABASE/////////////////////////////////////////////

        score = new Score(text, myScore);
        db.insert(randomID(), text, myScore);

    }

    //provvisoriamente l'id è un numero pseudo-casuale
    private String randomID() {
        Random gen = new Random();
        String str;

        return str = "" + gen.nextInt(10000);
    }

    public void setMultiplayer(boolean value) {
        this.isMultiplayer = value;
    }

    public void setLivelloCorrente(int livelloCorrente) {
        this.livelloCorrente = livelloCorrente;
    }
}
