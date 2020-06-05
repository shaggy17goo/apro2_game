package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.StrategicGame;

/**
 * Main class to make a window and start the game
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        StrategicGame game = new StrategicGame(22, 22);
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.addIcon("icons/logo128.png", Files.FileType.Internal);
        config.addIcon("icons/logo32.png", Files.FileType.Internal);
        config.addIcon("icons/logo16.png", Files.FileType.Internal);
        config.title = StrategicGame.GAME_NAME;
        config.width = StrategicGame.WIDTH;
        config.height = StrategicGame.HEIGHT;
        config.resizable = false;
        config.foregroundFPS = 60;
        new LwjglApplication(game, config);

    }
}
