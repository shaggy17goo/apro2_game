package Client.GraphicalSkills;

public class Heal extends Skill {
    public Heal(int index, int distance) {
        super("skillGraphics/arrow.png");
        this.distance = distance;
        this.index = index;
        this.value = +10;
        this.range = 1;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }
}
