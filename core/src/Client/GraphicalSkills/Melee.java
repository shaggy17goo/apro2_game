package Client.GraphicalSkills;

public class Melee extends Skill {
    public Melee(int value, int index) {
        super("skillGraphics/arrow.png");
        this.index=index;
        distance = 1;
        this.value = value;
        range = 0;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
