package com.mygdx.game.Graphics.sprites;

import com.mygdx.game.eccezioni.IllegalBrick;
import com.mygdx.game.eccezioni.IllegalPowerUp;
import com.mygdx.game.Graphics.sprites.Brick.Brick;
import com.mygdx.game.Graphics.sprites.Brick.HardBrick;
import com.mygdx.game.Graphics.sprites.Brick.NormalBrick;
import com.mygdx.game.Graphics.sprites.powerup.*;

/**
 * Descrizione generale classe
 *
 * @author
 */

public class SpriteFactory {

    static SpriteFactory instance;

    /**
     * descrizione metodo
     *
     * @return
     */
    public static synchronized SpriteFactory getInstance() {
        if (instance == null) {
            instance = new SpriteFactory();
        }
        return instance;
    }

    /**
     * descrizione metodo
     *
     * @param powerUp
     * @param posX
     * @param posY
     * @return
     * @throws IllegalPowerUp
     */
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

    /**
     * descrizione metodo
     *
     * @param powerUp
     * @param posX
     * @param posY
     * @return
     * @throws IllegalBrick
     */
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
