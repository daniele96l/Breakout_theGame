package sprites.powerup;

import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;
/**
 * @author regna, schillaci
 * Classe che rappresenta l'oggetto power up che restringe la paddle
 */

public class ShortPaddle extends PowerUp{


    public ShortPaddle(int posX, int posY) {
        super("little.png", posX, posY);
        this.sound="evil.mp3";
    }

    /**
     * Rende effettivo l'effetto del power up dopo essere stato preso dal giocatore
     * @param player il giocatore su cui avrà effetto
     * @param paddle la paddle che ha preso il power up
     * @param palla la palla che ha colpito il mattoncino che aveva il power up
     */

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        Info.paddleresizex.set(paddle.getGiocatore()-1,0.3f);
    }
}


