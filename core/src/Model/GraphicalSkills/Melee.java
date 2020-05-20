package Model.GraphicalSkills;

public class Melee extends Skill {
    public Melee(int index) {
        super("LOGO.png");
        this.index=index;
        distance = 1;
        value = -10;
        range = 0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
