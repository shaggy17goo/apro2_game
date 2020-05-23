package com.mygdx.game.desktop;

import Client.GameEngine;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.PixmapIO;
import com.mygdx.game.StrategicGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		StrategicGame game=new StrategicGame(22,22);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.addIcon("icons/logo128.png", Files.FileType.Internal);
		config.addIcon("icons/logo32.png", Files.FileType.Internal);
		config.addIcon("icons/logo16.png", Files.FileType.Internal);
		config.title = StrategicGame.GAME_NAME;
		config.width = StrategicGame.WIDTH;
		config.height = StrategicGame.HEIGHT;
		config.resizable = false;
		new LwjglApplication(game, config);

	}
}
