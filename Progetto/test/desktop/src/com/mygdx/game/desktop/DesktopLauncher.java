package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BreakGame;
import com.mygdx.game.help.Info;

/**
 * @author ligato,schillaci, regna
 *
 * Lancia il gioco, impostando le dimensioni della finestra ed il framerate
 */

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		config.width = Info.getInstance().getLarghezza();
		config.foregroundFPS = 60;
		config.height = Info.getInstance().getAltezza();
		new LwjglApplication(new BreakGame(), config);
	}
}
