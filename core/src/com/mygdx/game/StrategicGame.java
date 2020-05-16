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
    public static int WIDTH=700+20;
    public static int HEIGHT=700+20;
    private boolean paused;

    public StrategicGame(){
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
}
