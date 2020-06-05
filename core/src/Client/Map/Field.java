package Client.Map;

import Client.GraphicalHeroes.Hero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

import java.io.Serializable;
import java.util.Random;

/**
 * One piece of a map. A heo may stand one it, an obstacle may be placed on it.
 */
public class Field extends Image implements Serializable {
    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;

    //Logic representation
    private int mapX, mapY;
    private int type;
    //Entities staying on this field
    private Hero hero;
    private Obstacle obstacle;

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Field(int y, int x) {
        super(new Texture(texture()));
        this.setOrigin(WIDTH / 2, HEIGHT / 2);
        //Logic representation
        this.mapX = x;
        this.mapY = y;
        //GUI representation
        this.setSize(WIDTH, HEIGHT);
        this.setPosition(x * WIDTH + 10, StrategicGame.HEIGHT - (y + 1) * HEIGHT - 10);
    }

    public void addHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return this.hero;
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    // Should be Overridden in all inheriting classes
    @Override
    public String toString() {
        if (hero != null) return hero.toString();
        else if (obstacle != null) return obstacle.toString();
        else return "  ";   // empty field
    }

    // Randomizer for textures of background
    private static String texture() {
        Random rand = new Random();
        int i = rand.nextInt(5) + 1;
        return "fieldGraphics/grass" + i + ".png";


    }

}
