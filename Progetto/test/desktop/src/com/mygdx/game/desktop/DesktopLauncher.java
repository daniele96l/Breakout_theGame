package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.mygdx.game.BreakGame;
import help.Info;


public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		config.width = Info.larghezza;
		config.height = Info.altezza;
		new LwjglApplication(new BreakGame(), config);
	}
}
