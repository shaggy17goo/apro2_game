package Server.Model.Skills;

public class Heal extends Skill {
    public Heal(){
        this.distance=distance;
        this.value=+10;
        this.range=1;
        afterAttack=SkillProperty.GoToTarget;
        useDistance=SkillProperty.Flood;
        rangeType=SkillProperty.PointRange;
    }
}
