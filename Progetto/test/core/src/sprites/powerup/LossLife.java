package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

public class LossLife extends PowerUp{
    public LossLife(int posX, int posY) {
        super("losslife.png", posX, posY);
        this.sound="evil.mp3";

    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        player.setLives(player.getLives()-1);
    }
}
