package sprites.powerup;

import com.mygdx.game.Player.Player;
import sprites.Ball;
import sprites.Paddle;

public class ExtraLife extends PowerUp {
    public ExtraLife(int posX, int posY) {
        super("cuore.png", posX, posY);
        this.sound="good.mp3";
    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        player.setLives(player.getLives()+1);
    }
}
