package Model.LogicalSkills;


public class Arrow extends LogicalSkill {
    public Arrow(int index) {
        this.index=index;
        distance = 15;
        value = -20;
        range = 1;
        afterAttack= SkillProperty.StayOnSpot;
        useDistance= SkillProperty.NoLob;
        rangeType= SkillProperty.PointRange;
    }
}
