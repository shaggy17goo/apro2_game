package com.mygdx.game;

import Screens.SplashScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StrategicGame extends Game {
    public final static String GAME_NAME = "Strategiczna gra turowa";
    public static int WIDTH;
    public static int HEIGHT;
    private boolean paused;
    private static int OFFSET=20;
    private int controlSection=400;
    public static int MAXX,MAXY,CONTROLPANELX;
    public StrategicGame(int maxX,int maxY){
        MAXX = maxX;
        MAXY = maxY;
        CONTROLPANELX = 32*maxX + OFFSET +30;
        WIDTH = 32*maxX + OFFSET +controlSection;
        HEIGHT = 32*maxY + OFFSET;
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
