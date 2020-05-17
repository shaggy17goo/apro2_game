package Client.Model.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

public abstract class Skill extends Image {//brakowało mi lepszej nazyw - według mnie do zmiany
    protected int distance;     //maksymalny zasięg rzucenia
    protected int value;
    protected int range;        //zasięg rozchodzenia się po rzuceniu

    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;

    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;
    public String imagePath;
    protected int mapX,mapY;


    public Skill(String imagePath){
        super(new Texture(imagePath));
        //this.mapX=x;
        //this.mapY=y;

        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(mapX*WIDTH+10, StrategicGame.HEIGHT-(mapY+1)*HEIGHT-10);
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    protected int index;

    public int getDistance() {
        return distance;
    }

    public int getValue() {
        return value;
    }

    public int getRange() {
        return range;
    }

    public SkillProperty getAfterAttack() {
        return afterAttack;
    }

    public SkillProperty getUseDistance() {
        return useDistance;
    }

    public SkillProperty getRangeType() {
        return rangeType;
    }

}
