package Model.LogicalSkills;

public class Walk extends LogicalSkill {
    public Walk(int distance, int index) {
        this.index = index;
        skillName = "Walk";
        this.distance = distance;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }
}
