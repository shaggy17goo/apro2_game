package Model.LogicalSkills;

public class Jump extends Skill {
    public Jump(int range,int index) {
        this.index=index;
        this.range = range;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }

}
