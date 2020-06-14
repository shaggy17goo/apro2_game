package Model.LogicalSkills;

public class Melee extends LogicalSkill {
    public Melee(int value, int index) {
        this.index = index;
        skillName = "Melee";
        distance = 1;
        value = value;
        range = 0;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }
}
