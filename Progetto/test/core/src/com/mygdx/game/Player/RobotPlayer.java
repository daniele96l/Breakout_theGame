package com.mygdx.game.Player;

import com.badlogic.gdx.Input;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

/**
 * @Autor Schillaci
 * Gestisci il giocatore artificiale
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
     *
     *
     * @return il tasto premuto che darà la direzione alla paddle
     */
    @Override
    public int keyPressed() {
        if((palla.getBoundsBall().x+palla.getBoundsBall().getWidth()/2<paddle.getBounds().x+0.25*paddle.getBounds().getWidth())
                && palla.getBoundsBall().y<Info.altezza*2/3) {
            return Input.Keys.LEFT;
        }
        if((palla.getBoundsBall().x+palla.getBoundsBall().getWidth()/2>paddle.getBounds().x+0.75*paddle.getBounds().getWidth())
                && palla.getBoundsBall().y<Info.altezza/6) {
            return Input.Keys.RIGHT;
        }
        return Input.Keys.ANY_KEY;
    }
}
