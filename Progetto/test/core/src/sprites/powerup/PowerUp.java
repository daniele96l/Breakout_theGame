package sprites.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

import java.io.Serializable;

/**
 * Questa classe definisce l'oggetto astratto PowerUp, vale a dire un potenziamento/depotenziamento
 * (es. aggiunge/toglie vite al giocatore, allarga/diminuisce la larghezza della paddle)
 * che cade dal mattonicino appena colpito dal giocatore
 *
 * @author Alberto Schillaci
 */
public interface PowerUp {

    void effect(Player player, Paddle paddle, Ball palla);

    Rectangle getBounds();

    Vector2 getPosition();

    Vector2 getSpeed();

    String getSound();
}
