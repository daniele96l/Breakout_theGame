package com.mygdx.game.Screen;

import DatabaseManagement.Database;
import DatabaseManagement.Enum.DropType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
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
import sprites.Paddle;
import sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class OfflineGameScreen implements Screen {

    private BreakGame game;
    private Collision collision;
    private ArrayList<AbstractBrick> bricks = new ArrayList();
    private Ball palla;
    int contatore;
    private ArrayList<Paddle> paddles;
    private Texture bg;
    private GameState gameState;
    private ArrayList<CommandPlayer> commandPlayers;
    private ArrayList<Player> players;
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
    private boolean loop;
    private Music music;
    private Music music2, music3, music4;
    private boolean pause;
    private int numeroPlayer;
    private Hud hud;
    private Player gameHolder;  //Giocatore che ha toccato la pallina per ultimo
    private int brickCounter;
    private int tmpDT;
    private boolean isPaused;
    private boolean isFirstCalled;
    private ArrayList<Date> date;
    private Database db = new Database();

    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        this.numeroPlayer = numeroPlayer;
        this.game = game;
        players =new ArrayList<Player>(); //Pattern Expert, if Object A instanziate B it must have all the info to create it
        paddles = new ArrayList<Paddle>();//Pattern Expert, if Object A instanziate B it must have all the info to create it
        commandPlayers = new ArrayList<CommandPlayer>();//Pattern Expert, if Object A instanziate B it must have all the info to create it
        players.add(new HumanPlayer(playerName));//Pattern Expert, if Object A instanziate B it must have all the info to create it
        date = new ArrayList<Date>();
        isFirstCalled=true;
    }

    @Override
    public void show() {
        if(isFirstCalled) {
            isFirstCalled=false;
            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
            music2 = Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));
            music3 = Gdx.audio.newMusic(Gdx.files.internal("audio.mp3"));
            music4 = Gdx.audio.newMusic(Gdx.files.internal("evil.mp3"));//deve essere inzializzato in qualche modo, poi si cambia
            music3.setLooping(false);
            music4.setLooping(false);
            music.setVolume(1);
            isFinished = false;
            livelloCorrente = 1;
            winLoseState = new WinLoseState(game.getBatch(), gameState);
            nextLevel = false;
            isPaused = false;
            palla = new Ball();
            tmpDT = Info.dt;
            date.add(new Date());
            paddles.add(new Paddle(numeroPlayer, 1));
            Info.paddleresizex.add(0.5f);
            if (numeroPlayer > 1) {
                for (int i = 1; i < numeroPlayer; i++) {
                    paddles.add(new Paddle(numeroPlayer, i + 1));
                    players.add(new RobotPlayer("Robot " + i, palla, paddles.get(i)));
                    Info.paddleresizex.add(0.5f);
                    date.add(i,new Date());
                }
            }
            updateScene();
            updateLevel();
            hud = new Hud(players, game.getBatch());
            gameHolder = players.get(0);
            brickCounter = 0;
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }
    }

    @Override
    public void render(float delta) { // si potrebbe astrattizzare
        game.getBatch().begin();

        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }
        if(gameState!=GameState.GAME_OVER && gameState!=GameState.YOU_WON) {
            music.play();
        }
        palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y * Info.dt);
        palla.getBoundsBall().setPosition(palla.getPositionBall());
        ArrayList<PowerUp> tmpPUps=new ArrayList<PowerUp>();
        for(PowerUp p:powerUps) {
            if(p.getPosition().y+Info.powerUpHeight<0) {
                tmpPUps.add(p);
            }
            p.getPosition().add(p.getSpeed().x*Info.dt, p.getSpeed().y*Info.dt);
            p.getBounds().setPosition(p.getPosition());
        }
        for(PowerUp p:tmpPUps) {
            powerUps.remove(p);
        }

        game.getBatch().draw(bg, 0, 0);
        game.getBatch().end();

        hud=new Hud(players, game.getBatch());
        hud.stage.draw();

        game.getBatch().begin();
        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.brickresize, brick.getHeight() * Info.brickresize);
//disegnoimattoncini
        }
        for(PowerUp p:powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth()*Info.powerUpResize, p.getHeight()*Info.powerUpResize);
        }
        game.getBatch().draw(paddles.get(0), paddles.get(0).getPosition().x, paddles.get(0).getPosition().y, paddles.get(0).getWidth() * Info.paddleresizex.get(0), paddles.get(0).getHeight() * Info.paddleresize);
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.paddleresizex.get(i), paddles.get(i).getHeight() * Info.paddleresize);
                if(!isPaused) {
                    commandPlayers.get(i).move();
                }
            }
        }
        if(!isPaused) {
            commandPlayers.get(0).move();//mipermettedimuovereilgiocatore
        }
        if (commandPlayers.get(0).checkpause()) {
            music.pause();
            game.setScreen(new PauseScreen(game, this));
        }
        gestisciCollisioni();

        checktimer(); // controlla il tempo

        if (matEliminati == gestoreLivelli.getLivello(livelloCorrente - 1).getnMatMorbidi()) {
            gameState = GameState.YOU_WON;

            livelloCorrente++;
            if (livelloCorrente > gestoreLivelli.getNumeroLivelli()) {
                isFinished = true;
            }
        }

        if (palla.getPositionBall().y <= 0) {
            lostLife(palla.getPositionBall().x,false);
            updateScene();
        }


        if (gameState == GameState.YOU_WON) {
            if (isFinished) {
                livelloCorrente = 1;
                isFinished = false;
                db.modify(ranGen(), playerName, players.get(0).getScore(), DropType.INSERT);
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

            if(!loop)
            music2.play();
            loop = true;
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

        Vector2 size = Scaling.fit.apply(Info.larghezza, Info.altezza, width, height);
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

    public void gestisciCollisioni() {//Questa classe va spostata secondo me, violerebbe il pattern Hight Coesion
        //Hight coesion; A measure of how focused the responsability of a class are
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
            ArrayList<PowerUp> tempPowerUps=new ArrayList<PowerUp>();
            for(PowerUp p:powerUps) {
                if(collision.checkPowerUp(paddles.get(i), p)) {
                    tempPowerUps.add(p);
                    p.effect(players.get(paddles.indexOf(paddles.get(i))), paddles.get(i), palla);
                    music4 = Gdx.audio.newMusic(Gdx.files.internal(p.getSound()));
                    music4.play();
                    lostLife(palla.getPositionBall().x,true);
                    if(Info.paddleresizex.get(i) != Info.paddleresize){ // qua verifico che sia stato cambiato la resize una volta che prendo il powerup
                        date.set(i,new Date());
                    }
                }
            }
            for(PowerUp p:tempPowerUps) {
                powerUps.remove(p);
            }
        }

        if (!indici.isEmpty()) {
            if (indici.size() >= 2) {
                contatore = 0;
                contatore2 = 0;
                ArrayList<AbstractBrick> tempMatt = new ArrayList<sprites.Brick.AbstractBrick>();
                for (int i : indici) {
                    if(bricks.get(i).getPositionBrick().x < palla.getPositionBall().x ){ /////////////NUOVO
                        contatore++; /////////////NUOVO
                    }
                    if(bricks.get(i).getPositionBrick().x > palla.getPositionBall().x ){ /////////////NUOVO
                        contatore2++; /////////////NUOVO
                    }
                    tempMatt.add(bricks.get(i));

                }

                if(contatore == 2 || contatore2 == 2) //per un eventuale debugging
                    System.out.println("double hit from the side");
                else
                    System.out.println("double hif from the top/bottom");

                if(contatore ==1 && contatore2 == 1) /////////////NUOVO
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
                        music3.stop();
                        music3.play();
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
                    music3.stop();
                    music3.play();
                    players.get(players.indexOf(gameHolder)).setScore(gameHolder.getScore() + (int) Math.pow(2, brickCounter));
                    brickCounter++;
                }
            }
        }
    }

    public static void setPlayerName(String playerName) {
        OfflineGameScreen.playerName = playerName;
    }

    public static String getPlayerName() {
        return playerName;
    }

    private void lostLife(float positionX,boolean powerup) {  //Questa classe va spostata secondo me, violerebbe il pattern Hight Coesion
        //Hight coesion; A measure of how focused the responsability of a class are

        int range=Info.larghezza/numeroPlayer;
        Player loser=new RobotPlayer("default", palla, paddles.get(0));

        for(int i=0; i<numeroPlayer; i++) {
            if(positionX>=i*range && positionX<(i+1)*range) {
                loser=players.get(i);
            }
        }
        if(!powerup) {
            loser.setLives(loser.getLives() - 1);
            gameState = GameState.WAIT;
        }

        if(loser.getLives()<0) {
            if(players.get(0).equals(loser)) {
                db.modify(ranGen(), playerName, players.get(0).getScore(), DropType.INSERT);
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
        powerUps=new ArrayList<PowerUp>();
        bg=gestoreLivelli.getLivello(livelloCorrente-1).getBackground();
        matEliminati = 0;
    }

    private String ranGen () {
        Random n = new Random();
        String s = "" + n.nextInt(1000);
        return s;
    }

    private void checktimer(){
        if(date != null){
            Date date2 = new Date();
            for(int i =0; i < numeroPlayer;i++)
            if(date2.getTime() - date.get(i).getTime() > Info.durataPowerUp)
                Info.paddleresizex.set(i,Info.paddleresize);
        }
    }


}

