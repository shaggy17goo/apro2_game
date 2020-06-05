package Model.LogicalSkills;

import java.io.Serializable;

public abstract class LogicalSkill implements Serializable {
    protected int distance;     //how far a hero can throw it
    protected int value;
    protected int range;        //how far does it spread after hitting
    protected int index;
    protected String skillName;

    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;

    public int getDistance() {
        return distance;
    }

    public int getValue() {
        return value;
    }

    public int getRange() {
        return range;
    }

    public int getIndex() {
        return index;
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

    @Override
    public String toString() {
        return skillName;
    }
}
