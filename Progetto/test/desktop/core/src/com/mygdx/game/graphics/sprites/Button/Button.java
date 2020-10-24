package com.mygdx.game.graphics.sprites.Button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.help.Info;

/**
 * Questa classe semplifica il passaggio dei pulsanti e il controllo delle loro dimensioni.
 *
 * @author Schillaci
 */

public class Button extends Sprite {
    public Button(String path, int posY) {
        super(new Texture(path));
        setPosition(Info.getInstance().getLarghezza()/2-getWidth()/2, posY);
    }
}
