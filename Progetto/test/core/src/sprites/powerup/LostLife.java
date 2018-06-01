package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

/**
 * @author Cristian Regna, Alberto Schillaci
 * Questa classe rappresenta l'oggetto PowerUp che toglie
 * una vita al giocatore che lo prende
 */
public class LostLife extends AbstractPowerUp{
    public LostLife(int posX, int posY) {
        super("losslife.png", posX, posY);
        this.sound="evil.mp3";

    }


    /**
     * Questo metodo rende effettivo l'effetto del PowerUp
     * @param player il giocatore su cui il PowerUp ha effetto
     * @param paddle la paddle che ha preso il PowerUp
     * @param palla la palla che ha colpito il mattoncino contenente il PowerUp
     */
    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        player.setLives(player.getLives()-1);
    }
}
