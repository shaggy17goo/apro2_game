package Model.LogicalSkills;

public class Fireball extends LogicalSkill {
    public Fireball(int index) {
        this.index=index;
        distance = 5;
        value = -10;
        range = 3;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.FloodRange;
    }
}