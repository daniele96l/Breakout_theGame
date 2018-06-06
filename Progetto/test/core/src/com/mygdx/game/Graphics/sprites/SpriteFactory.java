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
    public PowerUp getPowerUp(String powerUp, int posX, int posY) throws IllegalPowerUp {
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
     * @param brick
     * @param posX
     * @param posY
     * @return
     * @throws IllegalBrick
     */
    public Brick getBrick(String brick, int posX, int posY) throws IllegalBrick {
        if(brick==null) {
            throw new IllegalBrick();
        }
        else if(brick.equals("NormalBrick")) {
            return new NormalBrick(posX, posY);
        }
        else if(brick.equals("HardBrick")) {
            return new HardBrick(posX, posY);
        }
        else throw new IllegalBrick();
    }
}
