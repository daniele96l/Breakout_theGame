package com.mygdx.game.graphics.sprites.powerup;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Paddle;

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
