package Model.LogicalSkills;

public class Necromancy extends LogicalSkill {
    public Necromancy(int index){
        this.index=index;
        skillName = "Necromancy";
        this.distance = 10;
        this.range = 1;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}