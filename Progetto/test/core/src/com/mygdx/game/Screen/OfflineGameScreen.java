package com.mygdx.game.Screen;

import DatabaseManagement.Database;
import DatabaseManagement.Enum.DropType;
import DatabaseManagement.Enum.TableType;
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
import com.mygdx.game.hud.Hud;
import help.GameState;
import help.*;
import sprites.Ball;
import sprites.Brick.Brick;
import sprites.Paddle;
import sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author ligato,schillaci, regna
 * Questa classe gestisce la logica della partita quando si sta giocando offline
 */
public class OfflineGameScreen implements Screen {

    private BreakGame game;
    private Collision collision;
    private ArrayList<Brick> bricks = new ArrayList();
    private Ball palla;
    int contatore;
    private ArrayList<Paddle> paddles;
    private Texture bg;
    private GameState gameState;
    private ArrayList<CommandPlayer> commandPlayers;
    private ArrayList<Player> players;
    private int contatore2 = 0;
    private boolean nextLevel;
    private ArrayList<Integer> brickIndexes;
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
    private boolean loop;
    private Music musicGame;
    private Music musicGameOver;
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
    private Timer timer;



    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        //Applicato pattern Creator
        this.numeroPlayer = numeroPlayer;
        this.game = game;
        players =new ArrayList<Player>(); //Pattern Expert, if Object A instanziates B it must have all the info to create it
        paddles = new ArrayList<Paddle>();//Pattern Expert, if Object A instanziates B it must have all the info to create it
        commandPlayers = new ArrayList<CommandPlayer>();//Pattern Expert, if Object A instanziates B it must have all the info to create it
        players.add(new HumanPlayer(playerName));//Pattern Expert, if Object A instanziates B it must have all the info to create it
        date = new ArrayList<Date>();
        palla = new Ball();
        isFirstCalled=true;
        timer = new Timer();


    }

    /**
     * Il metodo show si occupa di preparare gli oggetti gli oggetti che serviranno
     * durante la partita: Si selezionano le musifche, i booleani, la paddle, la scena e
     * le informazioni che usciranno in alto (HUD)
     */

    @Override
    public void show() {
        if(isFirstCalled) {
            isFirstCalled=false;
            musicGame = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
            musicGameOver = Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));
            musicGame.setVolume(1);
            isFinished = false;
            livelloCorrente = 1;
            nextLevel = false;
            isPaused = false;

            Info.getInstance().setDt(1);
            tmpDT = Info.getInstance().getDt();
            date.add(new Date());
            paddles.add(new Paddle(numeroPlayer, 1));
            Info.getInstance().getPaddleresizex().add(0.5f);

            if (numeroPlayer > 1) {
                for (int i = 1; i < numeroPlayer; i++) {
                    paddles.add(new Paddle(numeroPlayer, i + 1));
                    players.add(new RobotPlayer("Robot " + i, palla, paddles.get(i)));
                    Info.getInstance().getPaddleresizex().add(0.5f);
                    date.add(i,new Date());
                }
            }
            gestoreLivelli=new GestoreLivelli("fileLivelli.txt");
            updateScene();
            updateLevel();
            hud = new Hud(players, game.getBatch());
            gameHolder = players.get(0);
            brickCounter = 0;
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }
    }

    /**
     * Il metodo render aggiorna la schermata ogni frame e renderizza a schermo gli oggetti grafici:
     * Prende i mattoncini e li renderizza, renderizza il background, seleziona la musica adatta allo stato e ne fa il play
     * imposta la posizione della palla, gestisce l'arraylist dei powerUp (aggiungendoli o rimuovendoli), aggiorna la HUD
     * e controlla lo stato corrente del gioco
     * @param delta
     */

    @Override
    public void render(float delta) { // si potrebbe astrattizzare
        game.getBatch().begin();

        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
        }
        if(gameState!=GameState.GAME_OVER && gameState!=GameState.YOU_WON) {
            musicGame.play();
        }

        palla.getPositionBall().add(palla.getSpeedBall().x * Info.getInstance().getDt(), palla.getSpeedBall().y * Info.getInstance().getDt());
        palla.getBoundsBall().setPosition(palla.getPositionBall());
        ArrayList<PowerUp> tmpPUps=new ArrayList<PowerUp>();
        for(PowerUp p:powerUps) {
            if(p.getPosition().y+Info.getInstance().getPowerUpHeight()<0) {
                tmpPUps.add(p);
            }
            p.getPosition().add(p.getSpeed().x*Info.getInstance().getDt(), p.getSpeed().y*Info.getInstance().getDt());
            p.getBounds().setPosition(p.getPosition());
        }
        for(PowerUp p:tmpPUps) {
            powerUps.remove(p);
        }
        Drawer.drawMultiplayerOffline( game,  bg, bricks, players, powerUps,  paddles,  palla, numeroPlayer);
        if (numeroPlayer > 1) {
            for (int i = 1; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.getInstance().getPaddleresizex().get(i), paddles.get(i).getHeight() * Info.getInstance().getPaddleresize());
                if(!isPaused) {
                    commandPlayers.get(i).move();
                }
            }
        }

        if(!isPaused) {
            commandPlayers.get(0).move();
        }
        if (commandPlayers.get(0).checkpause()) {
            musicGame.pause();
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
            lostLife();
            updateScene();
        }
        //Qui si potrebbe applicare
        if (gameState == GameState.YOU_WON) {
            if (isFinished) {
                livelloCorrente = 1;
                isFinished = false;
                db.modify(""+(int)Math.random()*1000, playerName, players.get(0).getScore(), DropType.INSERT, TableType.OFFLINE);
                updateScene();
                updateLevel(); /////////////SERVE UNO SCREEN DI FINE GIOCO
                dispose();
                game.setScreen(new FinishScreen(game));
            } else {
                nextLevel = true;
                game.setScreen(new WinGameScreen(game,this,gameState));
                updateScene();
                updateLevel();
            }
            musicGame.stop();
        }
        if (gameState == GameState.GAME_OVER) {
            musicGame.stop();
            if(!loop)
            musicGameOver.play();
            loop = true;
            dispose();
            game.setScreen(new LoseGameScreen(game));
        }
        if(gameState== GameState.WAIT) {
            Info.getInstance().setDt(0);
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                Info.getInstance().setDt(tmpDT);
                gameState=GameState.ACTION;
            }
        }
        game.getBatch().end();
    }


    /**
     * Si occupa di ridimensionare la finestra
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;

        Vector2 size = Scaling.fit.apply(Info.getInstance().getLarghezza(), Info.getInstance().getAltezza(), width, height);
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

    /**
     * Questa classe si occupa di gestire le collisioni che avverranno tra la pallina e i mattoncini
     *
     * DEVE ESSERE TOLTA DA QUI
     */

    public void gestisciCollisioni() {

        collision = new Collision(palla, bricks, powerUps, paddles, players);

        int numeroEliminati = collision.checkBrickCollision();
        matEliminati+=numeroEliminati;
        gameHolder.setScore(gameHolder.getScore()+numeroEliminati);

        collision.checkBorderCollision();

        Player newGameHolder =players.get(collision.checkPaddleCollision(players.indexOf(gameHolder)));
        if(!newGameHolder.equals(gameHolder)) {
            gameHolder=newGameHolder;
        }

        collision.checkPowerUpCollision(date);
        ArrayList<Player> eliminabili=new ArrayList<Player>();
        for(int i=0; i<numeroPlayer;i++) {
            if(players.get(i).getLives()<0) {
                eliminabili.add(players.get(i));
            }
        }
        for(Player player:eliminabili) {
            deletePlayer(player);
        }
    }


    public static void setPlayerName(String playerName) {
        OfflineGameScreen.playerName = playerName;
    }

    public static String getPlayerName() {
        return playerName;
    }

    /**
     * Se il giocatore ha preso il power up che fa perdere la vita, faccio agire l'effetto
     */
    private void lostLife() {
        int range=Info.getInstance().getLarghezza()/numeroPlayer;
        Player loser=new RobotPlayer("default", palla, paddles.get(0));

        for(int i=0; i<numeroPlayer; i++) {
            if(palla.getPositionBall().x>=i*range && palla.getPositionBall().x<(i+1)*range) {
                loser=players.get(i);
            }
        }

        loser.setLives(loser.getLives()-1);
        gameState=GameState.WAIT;

        if(loser.getLives() < 0) {
            deletePlayer(loser);
        }
    }

    private void deletePlayer(Player loser) {
        if(players.get(0).equals(loser)) {
            db.modify(""+(int)Math.random()*1000, playerName, players.get(0).getScore(), DropType.INSERT, TableType.OFFLINE);
            gameState=GameState.GAME_OVER;
        }
        else {
            int index=players.indexOf(loser);
            players.remove(loser);
            paddles.remove(index);
            numeroPlayer--;
        }
    }

    /**
     * Aggiorna la scena con la nuova posizione del player e della palla
     */

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

    /**
     * aggiorna l'arraylist dei mattoncini, con il layout del prossimo livello
     */
    private void updateLevel() {

        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        powerUps=new ArrayList<PowerUp>();
        bg=gestoreLivelli.getLivello(livelloCorrente-1).getBackground();
        matEliminati = 0;


    }

    /**
     * Un timer che controlla il tempo, dato che i power-Up hanno una durata limitata
     * il funzionamento è delegato ad una classe apposita come consigliano i pattern di HighCoesion
     */
    private void checktimer(){
        timer.checkTimer(date, numeroPlayer );
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}

