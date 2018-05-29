package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Screen.MovePlayer;
import help.GameState;
import help.Info;
import sprites.Paddle;

/**
 * @Author Regna, Schillaci, Ligato
 * Si occupa di gestire i movimenti del Player
 */

public class CommandPlayer {

    private Paddle paddle;
    private Player player;
    int numeroGiocatori;
    int giocatore;
    private MovePlayer movePlayer;

    /**
     * Salva i seguenti valori nei parametri
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
        movePlayer = new MovePlayer();
    }

    /**??????????????????????????????????????????????????????????????????????
     * La classe che si occupa di muovere la paddle, secondo le frecce destra e sinistra
     * controllo quindi che non vada oltre ai bordi destro e sinistro
     */

    public void move()
    {

        movePlayer.MoveRobot(numeroGiocatori, giocatore, paddle, player);
    }

    /**??????????????????????????????????????????????????????????????????????????????
     * La classe che si occupa di muovere la paddle, secondo le frecce destra e sinistra
     * controllo quindi che non vada oltre ai bordi destro e sinistro
     */

    public void move(int key)
    {

        movePlayer.MoveIt( key , numeroGiocatori, giocatore, paddle);

    }

    /**
     * Controllo che il gioco sia in pausa
     * @return un booleano che indica se si è in pausa o meno
     */
    public boolean checkpause(){
        if(player.keyPressed()==Input.Keys.P){
            return true;
        }
        return false;
    }
}
