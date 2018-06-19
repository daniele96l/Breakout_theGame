package com.mygdx.game.logic.Player;

import com.badlogic.gdx.Input;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Paddle;

/**
 * Si occupa di gestire i movimenti del Player
 *
 * @author Regna, Schillaci,
 */

public class CommandPlayer {

    private Paddle paddle;
    private Player player;
    private int numeroGiocatori;
    private int giocatore;

    /**
     * Salva i seguenti valori nei parametri
     *
     * @param paddle la mattonella che muoviamo
     * @param player l'oggetto giocatore
     * @param numeroGiocatori il numero dei giocatori
     * @param giocatore il numero del giocatore che sta giocando
     */

    public CommandPlayer(Paddle paddle, Player player, int numeroGiocatori, int giocatore){
        this.paddle = paddle;
        this.player=player;
        this.numeroGiocatori=numeroGiocatori;
        this.giocatore=giocatore;
    }

    /**
     * Metodo che si occupa di muovere la paddle, dato l'input da tastiera dell'utente.
     *
     * @see Player per risalire al metodo keyPressed().
     */

    public void move()
    {
        move(player.keyPressed());
    }

    /**
     * La classe che si occupa di muovere la paddle, secondo le frecce destra e sinistra.
     * Si controlla quindi che non vada oltre ai bordi dello spazio disponibile per il paddle.
     *
     * @param key intero rappresentante l'input da tastiera
     */

    public void move(int key)
    {

        if(key==Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.getInstance().getLarghezza()/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere

                paddle.getPosition().add(-Info.getInstance().getVelPaddle(), 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(key==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.getInstance().getLarghezza()/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.getInstance().getPaddleresizex().get(giocatore-1) ) {
                paddle.getPosition().add(Info.getInstance().getVelPaddle(), 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }

    }

    /**
     * Controllo che il gioco sia in pausa
     *
     * @return boolean che indica se si è in pausa o meno
     */
    public boolean checkpause(){
        if(player.keyPressed()==Input.Keys.P){
            return true;
        }
        return false;
    }
}
