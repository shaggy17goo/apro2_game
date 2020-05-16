package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.StrategicGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = StrategicGame.GAME_NAME;
		config.width = StrategicGame.WIDTH;
		config.height = StrategicGame.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new StrategicGame(), config);
	}
}
