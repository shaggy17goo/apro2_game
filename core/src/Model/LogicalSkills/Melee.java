package Model.LogicalSkills;

public class Melee extends LogicalSkill {
    public Melee(int index) {
        this.index=index;
        distance = 1;
        value = -10;
        range = 0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
