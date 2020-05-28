package Model.LogicalSkills;

public class Jump extends LogicalSkill {
    public Jump(int distance,int index) {
        this.index=index;
        this.distance= distance;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }

}
