package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

public class LossLife extends PowerUp{
    private String sound = "evil.mp3";
    public LossLife(int posX, int posY) {
        super("losslife.png", posX, posY);
    }

    @Override
    public String getSound() {
        return sound;
    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        player.setLives(player.getLives()-1);
    }
}
