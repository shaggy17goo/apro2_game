package com.mygdx.game;

import Client.Screens.SplashScreen;
import com.badlogic.gdx.Game;

public class StrategicGame extends Game {
    public final static String GAME_NAME = "Strategiczna gra turowa";
    public static int WIDTH;
    public static int HEIGHT;
    public static int TEXTUREWIDTH;
    public static int TEXTUREHEIGHT;
    private boolean paused;
    private static int OFFSET=20;
    private int controlSection=400;
    public static int MAXX,MAXY,CONTROLPANELX;
    public StrategicGame(int maxX,int maxY){
        MAXX = maxX;
        MAXY = maxY;
        TEXTUREWIDTH = 32;
        TEXTUREHEIGHT = 32;
        CONTROLPANELX = TEXTUREWIDTH*maxX + OFFSET +30;
        WIDTH = TEXTUREWIDTH*maxX + OFFSET +controlSection;
        HEIGHT = TEXTUREHEIGHT*maxY + OFFSET;
    }
    @Override
    public void create () {
        this.setScreen(new SplashScreen(this));
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /*@Override
    public void render() {
        //Gdx.gl.glClearColor(0,0,0,0);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }*/
}
