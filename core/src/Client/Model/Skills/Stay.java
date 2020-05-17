package Client.Model.Skills;

public class Stay extends Skill{
    public Stay(int index){
        super("LOGO.png");
        this.index=index;
        distance=0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
