package sprites.powerup;

import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

public class ShortPaddle extends PowerUp{


    public ShortPaddle(int posX, int posY) {
        super("little.png", posX, posY);
        this.sound="evil.mp3";
    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {
        Info.paddleresizex.set(paddle.getGiocatore()-1,0.3f);
    }
}


