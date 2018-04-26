package com.mygdx.game.Screen;

import ClientServer.Dato;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Collision;
import com.mygdx.game.CommandPlayer;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Leaderboard.Score;
import com.mygdx.game.Levels.GestoreLivelli;
import com.mygdx.game.Player.HumanPlayer;
import com.mygdx.game.Player.RobotPlayer;
import com.mygdx.game.State.WinLoseState;
import help.GameState;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Paddle;

import java.util.ArrayList;

public class OfflineGameScreen implements Screen {

    private BreakGame game;
    private Collision col;
    private ArrayList<AbstractBrick> bricks = new ArrayList();
    private Ball palla;
    private ArrayList<Paddle> paddles;
    private Texture bg;
    private GameState gameState;
    private ArrayList<CommandPlayer> commandPlayers;
    private HumanPlayer player1;
    private ArrayList<RobotPlayer> robotPlayers;
    private Dato dato;
    private boolean nextLevel;
    private ArrayList<Integer> indici;
    private int matEliminati;
    private GestoreLivelli gestoreLivelli;
    private int livelloCorrente;
    private boolean isFinished;
    private Leaderboard leaderboard;
    static private int myScore;
    static private boolean nick;
    private Score score;
    private static String playerName;
    private WinLoseState winLoseState;
    Music music;
    Music music2;
    private boolean pause;
    BitmapFont bitmapFont;
    int LostLives = 0;
    private int numeroPlayer;


    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        this.numeroPlayer = numeroPlayer;
        this.game = game;
        player1 = new HumanPlayer(playerName);
        paddles = new ArrayList<Paddle>();
        commandPlayers = new ArrayList<CommandPlayer>();
    }

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));
        music.setVolume(1);
        leaderboard = new Leaderboard(gameState);
        isFinished = false;
        livelloCorrente = 1;
        winLoseState = new WinLoseState(game.getBatch(), gameState);
        nextLevel = false;
        paddles.add(new Paddle(0.5f, numeroPlayer, 1));
        commandPlayers.add(new CommandPlayer(paddles.get(0), player1, numeroPlayer, 1));
        if (numeroPlayer > 1) {
            robotPlayers = new ArrayList<RobotPlayer>();
            for (int i = 1; i < numeroPlayer; i++) {
                paddles.add(new Paddle(0.5f, numeroPlayer, i + 1));
                robotPlayers.add(new RobotPlayer("robot ", palla, paddles.get(i)));
                commandPlayers.add(new CommandPlayer(paddles.get(i), robotPlayers.get(i - 1), numeroPlayer, i + 1));
            }
        }
        reset();
        bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
    }


    @Override
    public void render(float delta) {

        game.getBatch().begin();


        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }

        music.play();
        palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y * Info.dt);
        palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);
        game.getBatch().draw(bg, 0, 0);

        for (AbstractBrick AbstractBrick : bricks) {
            game.getBatch().draw(AbstractBrick, AbstractBrick.getPositionBrick().x, AbstractBrick.getPositionBrick().y, AbstractBrick.getWidth() * Info.brickresize, AbstractBrick.getHeight() * Info.brickresize);
//disegnoimattoncini
        }


        game.getBatch().draw(paddles.get(0), paddles.get(0).getPosition().x, paddles.get(0).getPosition().y, paddles.get(0).getWidth() * Info.paddleresize, paddles.get(0).getHeight() * Info.paddleresize);
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);

        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.paddleresize, paddles.get(i).getHeight() * Info.paddleresize);
                commandPlayers.get(i).move();
            }
        }


        bitmapFont.draw(game.getBatch(), "Youlost:" + String.valueOf(LostLives) + "times", 20, 830);

        commandPlayers.get(0).move();//mipermettedimuovereilgiocatore
        if (commandPlayers.get(0).checkpause()) {
            music.stop();
            game.setScreen(new PauseScreen(game));
        }

         if (commandPlayers.get(0).checkpausesingle()) {
               Info.dt = 0;
               delta = 0;
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


        if (gameState == GameState.YOU_WON) {
            if (isFinished) {

                livelloCorrente = 1;
                isFinished = false;
                reset();
                game.setScreen(new FinishScreen(game));
            } else {
                nextLevel = true;
                winLoseState = new WinLoseState(game.getBatch(), gameState);
                gameState = winLoseState.draw();
                reset();
            }
            music.stop();
        }
        if (gameState == GameState.GAME_OVER) {
            winLoseState = new WinLoseState(game.getBatch(), gameState);
            gameState = winLoseState.draw();
            music.stop();
            reset();
        }


        game.getBatch().end();

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

    public void reset() {
        palla = new Ball();
        paddles.set(0, new Paddle(0.5f, numeroPlayer, 1));
        player1 = new HumanPlayer(playerName); // non so cosa ci va
        commandPlayers.set(0, new CommandPlayer(paddles.get(0), player1, numeroPlayer, 1));//istanziounCommandplayer(possoavernediversiperogniplayer
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                paddles.set(i, new Paddle(0.5f, numeroPlayer, i + 1));
                robotPlayers.set(i - 1, new RobotPlayer("robot ", palla, paddles.get(i)));
                commandPlayers.set(i, new CommandPlayer(paddles.get(i), robotPlayers.get(i - 1), numeroPlayer, i + 1));
            }
        }
        gestoreLivelli = new GestoreLivelli("fileLivelli.txt");
        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        matEliminati = 0;
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

        col.checkside(paddles.get(0));
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                col.checkside(paddles.get(i));
            }
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
    }

    public static void setPlayerName(String playerName) {
        OfflineGameScreen.playerName = playerName;
    }
}
