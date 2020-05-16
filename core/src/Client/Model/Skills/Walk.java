package Client.Model.Skills;

public class Walk extends Skill {
    public Walk(int distance){
        this.distance=distance;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
