package Model.LogicalSkills;

public class Stomp extends LogicalSkill {
    public Stomp(int index) {
        this.index = index;
        skillName = "Stomp";
        distance = 8;
        value = -8;
        range = 3;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.FloodRange;
    }
}
