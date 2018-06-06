package com.mygdx.game.logic.GameManager;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Collision;
import com.mygdx.game.logic.Player.CommandPlayer;
import com.mygdx.game.logic.Levels.GestoreLivelli;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.logic.Player.RobotPlayer;
import com.mygdx.game.help.GameState;
import com.mygdx.game.help.Info;
import com.mygdx.game.help.Timer;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Brick.Brick;
import com.mygdx.game.graphics.sprites.Paddle;
import com.mygdx.game.graphics.sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;

public abstract class GameManager {
    protected BreakGame game;
    protected Collision collision;
    protected ArrayList<Brick> bricks;
    protected Ball palla;
    protected ArrayList<Paddle> paddles;
    protected Texture bg;
    protected GameState gameState;
    protected ArrayList<CommandPlayer> commandPlayers;
    protected ArrayList<Player> players;
    protected boolean nextLevel;
    protected ArrayList<PowerUp> powerUps;
    protected int matEliminati;
    protected GestoreLivelli gestoreLivelli;
    protected int livelloCorrente;
    protected boolean isFinished;
    protected int numeroPlayer;
    protected Player gameHolder;  //Giocatore che ha toccato la pallina per ultimo
    protected int tmpDT;
    protected ArrayList<Date> date;
    protected Timer timer;

    /**
     * Il metodo render aggiorna la schermata ogni frame e renderizza a schermo gli oggetti grafici:
     * Prende i mattoncini e li renderizza, renderizza il background, seleziona la musica adatta allo stato e ne fa il play
     * imposta la posizione della palla, gestisce l'arraylist dei powerUp (aggiungendoli o rimuovendoli), aggiorna la HUD
     * e controlla lo stato corrente del gioco.
     *
     */

    public abstract void render();

    protected abstract void deletePlayer(Player loser);

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

    protected void lostLife() {
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

    /**
     * Aggiorna la scena con la nuova posizione del player e della palla
     */

    protected void updateScene() {
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
    protected void updateLevel() {

        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        powerUps=new ArrayList<PowerUp>();
        bg=gestoreLivelli.getLivello(livelloCorrente-1).getBackground();
        matEliminati = 0;

    }

    protected void checkTimerPowerUp() {
        timer.checkTimer(date, numeroPlayer );
    }
}
