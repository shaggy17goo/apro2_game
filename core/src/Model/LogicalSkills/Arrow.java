package Model.LogicalSkills;


public class Arrow extends LogicalSkill {
    public Arrow(int index) {
        this.index=index;
        skillName = "Arrow";
        distance = 5;
        value = -20;
        range = 1;
        afterAttack= SkillProperty.StayOnSpot;
        useDistance= SkillProperty.Lob;
        rangeType= SkillProperty.PointRange;
    }
}
