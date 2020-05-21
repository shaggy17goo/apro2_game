package Model.LogicalSkills;

public abstract class LogicalSkill {//brakowało mi lepszej nazyw - według mnie do zmiany
    protected int distance;     //maksymalny zasięg rzucenia
    protected int value;
    protected int range;        //zasięg rozchodzenia się po rzuceniu
    protected int index;

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

}
