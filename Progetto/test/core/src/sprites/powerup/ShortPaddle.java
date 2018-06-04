package sprites.powerup;

import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;


/**
 * Questa classe rappresenta l'oggetto PowerUp che restringe il paddle
 *
 * @author Cristian Regna, Alberto Schillaci
 */
public class ShortPaddle extends AbstractPowerUp{


    public ShortPaddle(int posX, int posY) {
        super("little.png", posX, posY);
        this.sound="evil.mp3";
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
        Info.getInstance().getPaddleresizex().set(paddle.getGiocatore()-1,0.3f);
    }
}


