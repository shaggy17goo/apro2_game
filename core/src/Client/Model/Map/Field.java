package Client.Model.Map;

import Client.Model.GraphicalHeroes.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

public class Field extends Image {
    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;


    private int mapX,mapY;
    private int type;
    //private List<Entity> entityList=new ArrayList<>();
    private Hero hero;
    private Obstacle obstacle;
    public Field(int y,int x){
        super(new Texture("grass1.png"));

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
    @Override
    public String toString(){ // should be Overridden in all inheriting classes
        if(hero!=null) return hero.toString();
        else if(obstacle!=null) return obstacle.toString();
        //else return "__";   // empty field
        else return "  ";   // empty field
    }

}
