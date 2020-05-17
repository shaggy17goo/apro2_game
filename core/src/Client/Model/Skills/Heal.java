package Client.Model.Skills;

public class Heal extends Skill {
    public Heal(){
        super("LOGO.png");
        this.distance=distance;
        this.value=+10;
        this.range=1;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
