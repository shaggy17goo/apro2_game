package Model.LogicalSkills;

public class Walk extends Skill {
    public Walk(int distance,int index){
        this.index=index;
        this.distance=distance;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
