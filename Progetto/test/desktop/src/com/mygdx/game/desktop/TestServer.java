package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ClientServer.Server;

/**
 * @author regna, schillaci
 * Fa partire il server
 */

public class TestServer {

    private Texture serverTexture;
    private Game game;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 60;
        new LwjglApplication(new Server(), config);
    }
}