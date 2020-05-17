package Client.Model.Skills;

public class Jump extends Skill {
    public Jump(int range,int index) {
        super("LOGO.png");
        this.index=index;
        this.range = range;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }

}
