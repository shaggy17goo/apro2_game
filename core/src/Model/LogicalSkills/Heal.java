package Model.LogicalSkills;

public class Heal extends LogicalSkill {
    public Heal(int index,int distance){
        this.index=index;
        this.distance=distance;
        this.value=+10;
        this.range=1;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
