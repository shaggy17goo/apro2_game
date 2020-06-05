package Model.LogicalSkills;

public class Charge extends LogicalSkill {
    public Charge(int value, int index) {
        this.index = index;
        skillName = "Charge";
        distance = 12;
        value = value;
        range = 0;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }
}
