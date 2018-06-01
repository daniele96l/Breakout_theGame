package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

/**
 * @author Cristian Regna, Alberto Schillaci
 * Questa classe rappresenta l'oggetto PowerUp che aggiunge
 * una vita al giocatore che lo prende
 */
public class ExtraLife extends AbstractPowerUp {
    public ExtraLife(int posX, int posY) {
        super("cuore.png", posX, posY);
        this.sound="good.mp3";
    }

    /**
     * Questo metodo rende effettivo l'effetto del PowerUp
     * @param player il giocatore su cui il PowerUp ha effetto
     * @param paddle la paddle che ha preso il PowerUp
     * @param palla la palla che ha colpito il mattoncino contenente il PowerUp
     */
    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        player.setLives(player.getLives()+1);
    }
}
