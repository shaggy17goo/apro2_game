package Client.Model.LogicalEntities;

import Client.Model.GraphicalSkills.SkillProperty;

public class LogicalSkill {
    protected int distance;     //maksymalny zasięg rzucenia
    protected int value;
    protected int range;        //zasięg rozchodzenia się po rzuceniu

    protected SkillProperty afterAttack;
    protected SkillProperty useDistance;
    protected SkillProperty rangeType;
    protected int mapX, mapY;

    public LogicalSkill(HeroSkill heroSkill, int distance) {
        switch (heroSkill) {
            case ARROW: {
                this.distance = distance;
                value = -20;
                range = 1;
                afterAttack = SkillProperty.StayOnSpot;
                useDistance = SkillProperty.NoLob;
                rangeType = SkillProperty.PointRange;
                break;
            }
            case FIREBALL: {
                this.distance = distance;
                value = -10;
                range = 3;
                afterAttack = SkillProperty.StayOnSpot;
                useDistance = SkillProperty.NoLob;
                rangeType = SkillProperty.FloodRange;
                break;
            }
            case HEAL: {
                this.distance = distance;
                value = +10;
                range = 1;
                afterAttack = SkillProperty.GoToTarget;
                useDistance = SkillProperty.Flood;
                rangeType = SkillProperty.PointRange;
                break;
            }
            case JUMP: {
                this.distance = distance;
                afterAttack = SkillProperty.GoToTarget;
                useDistance = SkillProperty.Lob;
                rangeType = SkillProperty.PointRange;
                break;
            }
            case MELEE: {
                this.distance = distance;
                value = -10;
                range = 0;
                afterAttack = SkillProperty.GoToTarget;
                useDistance = SkillProperty.Flood;
                rangeType = SkillProperty.PointRange;
                break;
            }
            case NECROMANCY: {
                this.distance = distance;
                range = 1;
                afterAttack = SkillProperty.GoToTarget;
                useDistance = SkillProperty.Flood;
                rangeType = SkillProperty.PointRange;
                break;
            }
            case STAY:
            case WALK: {
                this.distance = distance;
                afterAttack = SkillProperty.GoToTarget;
                useDistance = SkillProperty.Flood;
                rangeType = SkillProperty.PointRange;
                break;
            }
        }
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
