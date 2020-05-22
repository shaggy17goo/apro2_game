package com.mygdx.game;

import Client.Client;
import Client.GameEngine;
import Client.Screens.SplashScreen;
import Client.Player;
import Model.LogicalPlayer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import Client.CorrelationUtils;

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
    public LogicalPlayer logicalPlayer;
    public Player player;
    public static Client client;
    public static final int movesPerTour = 4;
    private Music music;



    public int mapSize = 22;
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
        skin = new Skin(Gdx.files.internal("skin/craftacular/skin/craftacular-ui.json"));
        playMusic();
        try {
            this.setScreen(new SplashScreen(this));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void playMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("soundEffects/Gwent.mp3"));
        music.setLooping(true);
        music.setVolume(0.6f);
        music.play();
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void createPlayer(){
        this.player=new Player(nick);
        this.logicalPlayer = CorrelationUtils.makeLogicalPlayerFromGraphical(player);
    }

}
