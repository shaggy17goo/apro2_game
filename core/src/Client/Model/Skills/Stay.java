package Client.Model.Skills;

public class Stay extends Skill{
    public Stay(){
        super("LOGO.png");
        distance=0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
