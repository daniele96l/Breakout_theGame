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

abstract class GameManager {
    BreakGame game;
    ArrayList<Brick> bricks;
    Ball palla;
    ArrayList<Paddle> paddles;
    Texture bg;
    GameState gameState;
    ArrayList<CommandPlayer> commandPlayers;
    ArrayList<Player> players;
    boolean nextLevel;
    ArrayList<PowerUp> powerUps;
    int matEliminati;
    GestoreLivelli gestoreLivelli;
    int livelloCorrente;
    boolean isFinished;
    int numeroPlayer;
    Player gameHolder;  //Giocatore che ha toccato la pallina per ultimo
    int tmpDT;
    ArrayList<Date> date;
    Timer timer;

    /**
     * il metodo permette di eliminare il giocatore passato per parametro che ha perso tutte le sue vite.
     *
     * @param loser il giocatore in questione
     */
    protected abstract void deletePlayer(Player loser);

    /**
     * il metodo permette di controllare il numero di mattoncini eliminati da chi ha toccato per ultimo la palla.
     * Permette poi di capire chi è il nuovo game holder, cioè chi ha toccato per ultimo la palla.
     * Fa un check dei power up. Inoltre permette di eliminare dal gioco tutti i giocatori che non hanno più vite.
     *
     */
    void gestisciCollisioni() {

        Collision collision = new Collision(palla, bricks, powerUps, paddles, players);

        int numeroEliminati = collision.checkBrickCollision();
        matEliminati += numeroEliminati;
        gameHolder.setScore(gameHolder.getScore()+numeroEliminati);

        collision.checkBorderCollision();

        Player newGameHolder = players.get(collision.checkPaddleCollision(players.indexOf(gameHolder)));
        if(!newGameHolder.equals(gameHolder)) {
            gameHolder = newGameHolder;
        }

        collision.checkPowerUpCollision(date);
        ArrayList<Player> eliminabili = new ArrayList<Player>();
        for(int i=0; i<numeroPlayer;i++) {
            if(players.get(i).getLives()<0) {
                eliminabili.add(players.get(i));
            }
        }
        for(Player player:eliminabili) {
            deletePlayer(player);
        }
    }

    /**
     * il metodo permette di capire chi tra i giocatori in gioco nello scenario corrente ha perso la vita.
     * Il concetto è che i giocatori si dividono lo spazio a disposizione delle paddle in ugual modo,
     * se la pallina cade nel range di appartenenza dell'i-esimo paddle, il giocatore che lo controlla allora
     * perderà una vita. Se tale giocatore ha perso tutte le vite, allora viene eliminato dal gioco.
     */

    void lostLife() {
        int range = Info.getInstance().getLarghezza()/numeroPlayer;
        Player loser = new RobotPlayer("default", palla, paddles.get(0));

        for(int i=0; i<numeroPlayer; i++) {
            if(palla.getPositionBall().x >= i*range && palla.getPositionBall().x<(i+1)*range) {
                loser = players.get(i);
            }
        }

        loser.setLives(loser.getLives()-1);
        gameState = GameState.WAIT;

        if(loser.getLives() < 0) {
            deletePlayer(loser);
        }
    }

    /**
     * Aggiorna la scena con la nuova posizione del player e della palla
     */

    void updateScene() {
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
    void updateLevel() {

        bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//laclasselivellosioccuperàdiritornarel'arraylistdeimattonciniadattiaquestolivello
        powerUps=new ArrayList<PowerUp>();
        bg=gestoreLivelli.getLivello(livelloCorrente-1).getBackground();
        matEliminati = 0;

    }

    /**
     * il metodo controlla la durata temporale dei power up.
     */
    void checkTimerPowerUp() {
        timer.checkTimer(date, numeroPlayer);
    }
}
