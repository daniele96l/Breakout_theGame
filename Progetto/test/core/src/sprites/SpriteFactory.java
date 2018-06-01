package sprites;

import eccezioni.IllegalBrick;
import eccezioni.IllegalPowerUp;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;
import sprites.Brick.NormalBrick;
import sprites.powerup.*;

public class SpriteFactory {

    static SpriteFactory instance;

    public static synchronized SpriteFactory getInstance() {
        if (instance == null) {
            instance = new SpriteFactory();
        }
        return instance;
    }

    public static PowerUp getPowerUp(String powerUp, int posX, int posY) throws IllegalPowerUp {
        if(powerUp==null) {
            throw new IllegalPowerUp();
        }
        else if(powerUp.equals("ExtraLife")) {
            return new ExtraLife(posX, posY);
        }
        else if(powerUp.equals("LongPaddle")) {
            return new LongPaddle(posX, posY);
        }
        else if(powerUp.equals("LostLife")) {
            return new LostLife(posX, posY);
        }
        else if(powerUp.equals("ShortPaddle")) {
            return new ShortPaddle(posX, posY);
        }
        else throw new IllegalPowerUp();
    }

    public static Brick getBrick(String powerUp, int posX, int posY) throws IllegalBrick {
        if(powerUp==null) {
            throw new IllegalBrick();
        }
        else if(powerUp.equals("NormalBrick")) {
            return new NormalBrick(posX, posY);
        }
        else if(powerUp.equals("HardBrick")) {
            return new HardBrick(posX, posY);
        }
        else throw new IllegalBrick();
    }
}
