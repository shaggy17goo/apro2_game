package Client.Model;

import Client.Model.GraphicalSkills.Skill;
import Client.Model.GraphicalSkills.SkillProperty;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

public class LogicalSkill {//brakowało mi lepszej nazyw - według mnie do zmiany
    protected int distance;     //maksymalny zasięg rzucenia
    protected int value;
    protected int range;        //zasięg rozchodzenia się po rzuceniu

    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;


    public LogicalSkill(int distance, int value, int range, SkillProperty afterAttack, SkillProperty useDistance, SkillProperty rangeType) {
        this.distance = distance;
        this.value = value;
        this.range = range;
        this.afterAttack = afterAttack;
        this.useDistance = useDistance;
        this.rangeType = rangeType;
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
