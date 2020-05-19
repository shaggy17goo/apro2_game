package Client.Model.LogicalEntities;

import Client.Model.GraphicalSkills.SkillProperty;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.StrategicGame;

public class LogicalSkill {
    protected int distance;     //maksymalny zasięg rzucenia
    protected int value;
    protected int range;        //zasięg rozchodzenia się po rzuceniu

    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;
    protected int mapX, mapY;


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
