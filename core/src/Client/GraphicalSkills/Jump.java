package Client.GraphicalSkills;

public class Jump extends Skill {
    public Jump(int distance,int index) {
        super("skillGraphics/jump.png");
        this.index=index;
        this.distance = distance;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }

}
