package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.mygdx.game.Player.Player;
import com.mygdx.game.logic.MovePlayer;
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

        movePlayer.MoveRobot( numeroGiocatori, giocatore, paddle, player);
        /*if(player.keyPressed()==Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.larghezza/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere

                paddle.getPosition().add(-Info.getVelPaddle(), 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(player.keyPressed()==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.larghezza/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.paddleresizex.get(giocatore-1) ) {
                paddle.getPosition().add(Info.getVelPaddle(), 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }*/
    }

    /**??????????????????????????????????????????????????????????????????????????????
     * La classe che si occupa di muovere la paddle, secondo le frecce destra e sinistra
     * controllo quindi che non vada oltre ai bordi destro e sinistro
     */

    public void move(int key)
    {



        movePlayer.MoveIt( key , numeroGiocatori, giocatore, paddle);

       /*
        if(key==Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.larghezza/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere

                paddle.getPosition().add(-10, 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(key==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.larghezza/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.paddleresizex.get(giocatore-1) ) {
                paddle.getPosition().add(10, 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        */
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
