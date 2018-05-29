package com.mygdx.game.logic;

import com.badlogic.gdx.Input;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Paddle;


/**
 * @author Ligato
 *
 * Questa classe permette di gestire il movimento dei paddle, siano essi controllati da uno HumanPlayer o da un RobotPlayer
 *
 */

public class MovePlayer {

    /**
     *
     * @param key è un intero che rappresta il tasto premuto, cioè destra o sinistra.
     * @param numeroGiocatori
     * @param giocatore è il numero del gicatore, tra i partecipanti alla partita, che deve effettuare lo spostamento.
     * @param paddle è il paddle appartenente a tale giocatore
     */


    public void MoveIt(int key , int numeroGiocatori, int giocatore, Paddle paddle){
        if(key== Input.Keys.LEFT){
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

    }

    /**
     *
     * I parametri sono gli stessi del metodo MoveIt(...)
     * @param numeroGiocatori
     * @param giocatore
     * @param paddle
     * @param player in questo caso verra passato un tipo RobotPlayer in modo tale che, con il metodo keyPressed(), si ottenga lo spostamento del Robot.
     *
     * @see com.mygdx.game.Player.RobotPlayer
     */
    public void MoveRobot(int numeroGiocatori, int giocatore, Paddle paddle, Player player){

        if(player.keyPressed()==Input.Keys.LEFT){
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
        }

    }
}
