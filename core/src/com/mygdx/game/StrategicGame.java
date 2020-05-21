package com.mygdx.game;

import Client.GameEngine;
import Client.Screens.SplashScreen;
import Client.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StrategicGame extends Game {
    public final static String GAME_NAME = "Strategiczna gra turowa";
    public static int WIDTH;
    public static int HEIGHT;
    public static int TEXTUREWIDTH;
    public static int TEXTUREHEIGHT;
    public static GameEngine gameEngine;
    private boolean paused;
    private static int OFFSET = 20;
    private int controlSection = 400;
    public static int MAXX, MAXY, CONTROLPANELX;
    public Skin skin;
    public boolean[] choseHeroes;


    public int mapSize = 22;
    public Player player;
    public String nick;
    public String ip;
    public String port;

    public StrategicGame(int maxX, int maxY) {
        MAXX = maxX;
        MAXY = maxY;
        TEXTUREWIDTH = 32;
        TEXTUREHEIGHT = 32;
        CONTROLPANELX = TEXTUREWIDTH * maxX + OFFSET + 30;
        WIDTH = TEXTUREWIDTH * maxX + OFFSET + controlSection;
        HEIGHT = TEXTUREHEIGHT * maxY + OFFSET;
        choseHeroes = new boolean[6];
    }

    @Override
    public void create() {
        try {
            this.setScreen(new SplashScreen(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gameEngine = new GameEngine(mapSize, mapSize);
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

}
