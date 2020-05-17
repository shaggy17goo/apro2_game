package Client.Model.Skills;

public class Melee extends Skill {
    public Melee() {
        super("LOGO.png");
        distance = 1;
        value = -10;
        range = 0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
