package com.mygdx.game.Screen;

import ClientServer.Dato;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Collision;
import com.mygdx.game.CommandPlayer;
import com.mygdx.game.Leaderboard.Leaderboard;
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
import sprites.Paddle;

import java.util.ArrayList;

public class OfflineGameScreen implements Screen {

    private BreakGame game;
    private Collision collision;
    private ArrayList<AbstractBrick> bricks = new ArrayList();
    private Ball palla;
    private ArrayList<Paddle> paddles;
    private Texture bg;
    private GameState gameState;
    private ArrayList<CommandPlayer> commandPlayers;
    private ArrayList<Player> players;
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
    private int newHeight, newWight;
    private static String playerName;
    private WinLoseState winLoseState;
    Music music;
    Music music2;
    private boolean pause;
    private int numeroPlayer;
    private Hud hud;
    private Player gameHolder;  //Giocatore che ha toccato la pallina per ultimo
    private int brickCounter;
    private int tmpDT;


    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        this.numeroPlayer = numeroPlayer;
        this.game = game;
        players =new ArrayList<Player>();
        paddles = new ArrayList<Paddle>();
        commandPlayers = new ArrayList<CommandPlayer>();
        players.add(new HumanPlayer(playerName));
    }

    @Override
    public void show() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));
        music.setVolume(1);
        leaderboard = new Leaderboard(gameState);
        isFinished = false;
        livelloCorrente = 1;
        winLoseState = new WinLoseState(game.getBatch(), gameState);
        nextLevel = false;
        palla=new Ball();
        tmpDT=Info.dt;
        paddles.add(new Paddle(numeroPlayer, 1));
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                paddles.add(new Paddle( numeroPlayer, i + 1));
                players.add(new RobotPlayer("Robot "+i, palla, paddles.get(i)));
            }
        }
        updateScene();
        updateLevel();
        hud=new Hud(players, game.getBatch());
        gameHolder= players.get(0);
        brickCounter=0;
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

        game.getBatch().end();

        hud=new Hud(players, game.getBatch());
        hud.stage.draw();

        game.getBatch().begin();

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
            lostLife(palla.getPositionBall().x);
            updateScene();
        }


        if (gameState == GameState.YOU_WON) {
            if (isFinished) {

                livelloCorrente = 1;
                isFinished = false;
                updateScene();
                updateLevel(); /////////////SERVE UNO SCREEN DI FINE GIOCO
                game.setScreen(new FinishScreen(game));
            } else {
                nextLevel = true;
                winLoseState = new WinLoseState(game.getBatch(), gameState);
                gameState = winLoseState.draw();
                updateScene();
                updateLevel();
            }
            music.stop();
        }
        if (gameState == GameState.GAME_OVER) {
            music.stop();
            music2.play();
            winLoseState = new WinLoseState(game.getBatch(), gameState);
            gameState = winLoseState.draw();
            if(gameState==GameState.MENU) {
                game.setScreen(new MainMenuScreen(game));
            }
        }

        if(gameState== GameState.WAIT) {
            Info.dt=0;
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                Info.dt=tmpDT;
                gameState=GameState.ACTION;
            }
        }

        game.getBatch().end();

    }


    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;


        Vector2 size = Scaling.fit.apply(800, 850, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

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

    public void gestisciCollisioni() {
        float oldSpeedBallX = palla.getSpeedBall().x;
        float oldSpeedBallY = palla.getSpeedBall().y;

        indici = new ArrayList<Integer>();
        for (AbstractBrick Abstractbrick : bricks) {
            collision = new Collision(Abstractbrick, palla);
            if (collision.check()) {
                indici.add(bricks.indexOf(Abstractbrick));
            }
        }

        collision.checkBorderCollision();

        if(collision.checkSide(paddles.get(0))) {
            gameHolder = players.get(0);
            brickCounter = 0;
        }
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                if(collision.checkSide(paddles.get(i))) {
                    gameHolder = players.get(i);
                    brickCounter = 0;
                }
            }
        }

        if (!indici.isEmpty()) {
            if (indici.size() >= 2) {
                ArrayList<AbstractBrick> tempMatt = new ArrayList<sprites.Brick.AbstractBrick>();
                palla.setSpeedBall(new Vector2(oldSpeedBallX, -oldSpeedBallY));
                for (int i : indici) {
                    System.out.println(i);
                    tempMatt.add(bricks.get(i));
                }
                for (AbstractBrick brick : tempMatt) {
                    if (brick instanceof Brick) {
                        bricks.remove(brick);
                        matEliminati++;
                        players.get(players.indexOf(gameHolder)).setScore(gameHolder.getScore()+(int)Math.pow(2,brickCounter));
                        brickCounter++;
                    }
                }
            } else {
                if (bricks.get(indici.get(0)) instanceof Brick) {//seimattoncinisonomattoncini"morbidi"lipossoeliminare
                    bricks.remove((int) indici.get(0));
                    matEliminati++;
                    players.get(players.indexOf(gameHolder)).setScore(gameHolder.getScore()+(int)Math.pow(2,brickCounter));
                    brickCounter++;
                }
            }
        }
    }

    public static void setPlayerName(String playerName) {
        OfflineGameScreen.playerName = playerName;
    }

    private void lostLife(float positionX) {
        int range=Info.larghezza/numeroPlayer;
        Player loser=new RobotPlayer("default", palla, paddles.get(0));
        for(int i=0; i<numeroPlayer; i++) {
            if(positionX>=i*range && positionX<(i+1)*range) {
                loser=players.get(i);
            }
        }

        loser.setLives(loser.getLives()-1);
        gameState=GameState.WAIT;
        if(loser.getLives()<0) {
            if(players.get(0).equals(loser)) {
                gameState=GameState.GAME_OVER;
            }
            else {
                int index=players.indexOf(loser);
                players.remove(loser);
                paddles.remove(index);
                numeroPlayer--;
            }
        }
    }

    private void updateScene() {
        palla.setDefaultState();
        for(Paddle paddle:paddles) {
            paddle.setDefaultState(numeroPlayer);
        }
        commandPlayers=new ArrayList<CommandPlayer>();
        for(int i=0; i<players.size(); i++) {
            commandPlayers.add(new CommandPlayer(paddles.get(i), players.get(i), numeroPlayer, i+1));
        }
    }

    private void updateLevel() {
        gestoreLivelli = new GestoreLivelli("fileLivelli.txt");
        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        bg=gestoreLivelli.getLivello(livelloCorrente-1).getBackground();
        matEliminati = 0;
    }
}
