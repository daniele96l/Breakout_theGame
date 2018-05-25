package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

/**
 * @author regna, schillaci
 * Classe che rappresenta l'oggetto power up che da una vita extra al giocatore
 */
public class ExtraLife extends PowerUp {
    public ExtraLife(int posX, int posY) {
        super("cuore.png", posX, posY);
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
        player.setLives(player.getLives()+1);
    }
}
