package sprites.powerup;

import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

public class ShortPaddle extends PowerUp{


    private String sound = "good.mp3";

    public ShortPaddle(int posX, int posY) {
        super("little.png", posX, posY);
    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        Info.paddleresizex.set(paddle.getGiocatore()-1,0.3f);
    }

    @Override
    public String getSound() {
        return sound;
    }
}


