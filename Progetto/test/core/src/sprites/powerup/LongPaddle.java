package sprites.powerup;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

import java.util.concurrent.CountDownLatch;

/**
 * @author regna, schillaci
 * Classe che rappresenta l'oggetto power up che allarga la mattonella
 */
public class LongPaddle extends PowerUp {

    public LongPaddle(int posX, int posY) {
        super("big.png", posX, posY);
        this.sound="good.mp3";

    }


    /**
     * Rende effettivo l'effetto del power up dopo essere stato preso dal giocatore
     * @param player il giocatore su cui avrà effetto
     * @param paddle la paddle che ha preso il power up
     * @param palla la palla che ha colpito il mattoncino che aveva il power up
     */
    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {

        Info.paddleresizex.set(paddle.getGiocatore() - 1, 0.7f);
    }
}
