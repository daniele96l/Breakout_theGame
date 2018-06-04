/*
* DA FARE:
*
*
* */

package com.mygdx.game.Player;

import com.badlogic.gdx.Input;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

/**
 * La classe crea e gestisce il giocatore artificiale
 *
 * @author Schillaci
 */
public class RobotPlayer extends Player{
    private Ball palla;
    private Paddle paddle;
    public RobotPlayer(String playerName,Ball palla, Paddle paddle) {
        super(playerName);
        this.palla=palla;
        this.paddle=paddle;
    }

    /**
     * Il metodo ritorna un valore intero che corrisponde alla pressione, fittizia, del tasto LEFT o RIGHT:
     * infatti il metodo gestisce lo spostamento del paddle controllato dal robot facendo un check sulla posizione
     * della pallina.
     *
     * @return il tasto premuto che darà la direzione alla paddle
     */
    @Override
    public int keyPressed() {
        if((palla.getBoundsBall().x+palla.getBoundsBall().getWidth()/2<paddle.getBounds().x+0.25*paddle.getBounds().getWidth())
                && palla.getBoundsBall().y<Info.getInstance().getAltezza()/3) {
            return Input.Keys.LEFT;
        }
        if((palla.getBoundsBall().x+palla.getBoundsBall().getWidth()/2>paddle.getBounds().x+0.75*paddle.getBounds().getWidth())
                && palla.getBoundsBall().y<Info.getInstance().getAltezza()/3) {
            return Input.Keys.RIGHT;
        }
        return Input.Keys.ANY_KEY;
    }
}
