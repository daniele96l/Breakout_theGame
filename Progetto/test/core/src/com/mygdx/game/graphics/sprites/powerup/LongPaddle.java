package com.mygdx.game.graphics.sprites.powerup;

import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Paddle;

/**
 * Questa classe rappresenta l'oggetto PowerUp che allarga il paddle
 *
 * @author Cristian Regna, Alberto Schillaci
 */
public class LongPaddle extends AbstractPowerUp {

    public LongPaddle(int posX, int posY) {
        super("big.png", posX, posY);
        this.sound="good.mp3";

    }

    /**
     * Questo metodo rende effettivo l'effetto del PowerUp
     *
     * @param player il giocatore su cui il PowerUp ha effetto
     * @param paddle la paddle che ha preso il PowerUp
     * @param palla la palla che ha colpito il mattoncino contenente il PowerUp
     */
    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        Info.getInstance().getPaddleresizex().set(paddle.getGiocatore() - 1, 0.7f);
    }
}
