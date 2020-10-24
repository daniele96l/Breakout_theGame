package com.mygdx.game.graphics.sprites.Brick;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.graphics.sprites.powerup.PowerUp;

/**
 * La classe astratta Brick extende la classe di libGDX
 * chiama Sprite e contiene tutte le informazioni e variabili del "mattoncino base",
 * quali per esempio la sua posizione.
 *
 * @author Daniele Ligato
 */
public interface Brick {


    void setPowerUp(PowerUp powerUp);

    boolean hasPowerUp();

    PowerUp getPowerUp();

    Vector2 getPositionBrick();

    boolean isDeletable();

    String getRelativePosition(Brick brick);

    Rectangle getBoundsBrick();

}
