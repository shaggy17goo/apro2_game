package Client.Model.Skills;

public class Arrow extends Skill{
    public Arrow(int index) {
        super("LOGO.png");
        this.index=index;
        distance = 15;
        value = -20;
        range = 1;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.PointRange;
    }
}
