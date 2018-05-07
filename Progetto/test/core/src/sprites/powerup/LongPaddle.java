package sprites.powerup;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

import java.util.concurrent.CountDownLatch;

public class LongPaddle extends PowerUp {
    private String sound = "good.mp3";

    public LongPaddle(int posX, int posY) {
        super("big.png", posX, posY);
    }

    @Override
    public void effect(Player player, Paddle paddle, Ball palla) {

       Info.paddleresizex.set(paddle.getGiocatore()-1,0.7f);
    }

    @Override
    public String getSound() {
        return sound;
    }
}
