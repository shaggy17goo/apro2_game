package Client.GraphicalSkills;

public class Necromancy extends Skill {
    public Necromancy(int index){
        super("skillGraphics/arrow.png");
        this.distance = 10;
        this.range = 1;
        this.index=index;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}