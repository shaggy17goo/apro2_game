package Client.GraphicalSkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

import java.io.Serializable;

public abstract class Skill extends Image implements Serializable {
    protected int distance;// how far you can throw it
    protected int value;
    protected int range;// how far it spreads after a hit

    //Three properties describing all skills
    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;

    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public String imagePath;
    protected int mapX, mapY;
    protected int index;

    public Skill(String imagePath) {
        super(new Texture(imagePath));
        this.setOrigin(WIDTH / 2, HEIGHT / 2);
        this.setSize(WIDTH, HEIGHT);
        this.setPosition(mapX * WIDTH + 10, StrategicGame.HEIGHT - (mapY + 1) * HEIGHT - 10);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }



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
