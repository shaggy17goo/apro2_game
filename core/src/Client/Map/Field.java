package Client.Map;

import Client.GraphicalHeroes.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

import java.io.Serializable;
import java.util.Random;

public class Field extends Image implements Serializable {
    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;

    //Logic representation
    private int mapX,mapY;
    private int type;
    //Entities staying on this field
    private Hero hero;
    private Obstacle obstacle;
    public Field(int y,int x){
        super(new Texture(texture()));
        this.setOrigin(WIDTH/2,HEIGHT/2);
        //Logic representation
        this.mapX=x;
        this.mapY=y;
        //GUI representation
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(x*WIDTH+10, StrategicGame.HEIGHT-(y+1)*HEIGHT-10);
    }
    public void addHero(Hero hero){
            this.hero=hero;
    }
    public Hero getHero(){
        return this.hero;
    }
    public void addObstacle(Obstacle obstacle){
        this.obstacle=obstacle;
    }
    public Obstacle getObstacle(){
        return this.obstacle;
    }
    // Should be Overridden in all inheriting classes
    @Override
    public String toString(){
        if(hero!=null) return hero.toString();
        else if(obstacle!=null) return obstacle.toString();
        else return "  ";   // empty field
    }
    // Randomizer for textures of background
    private static String texture(){
        Random rand = new Random();
        int i=rand.nextInt(5)+1;
        return "fieldGraphics/grass"+i+".png";


    }

}
