package Client.Model.Skills;

public class Necromancy extends Skill {
    public Necromancy(){
        super("LOGO.png");
        this.distance = 10;
        this.range = 1;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}