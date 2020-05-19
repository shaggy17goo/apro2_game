package Server.Model.Skills;

public class Jump extends Skill {
    public Jump(int range) {
        this.range = range;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }

}
