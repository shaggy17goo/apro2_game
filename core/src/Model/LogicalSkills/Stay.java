package Model.LogicalSkills;

public class Stay extends LogicalSkill {
    public Stay(int index) {
        this.index = index;
        skillName = "Stay";
        distance = 0;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }
}
