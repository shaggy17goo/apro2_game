package Server.Model.Skills;

public class Stay extends Skill{
    public Stay(){
        distance=0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
