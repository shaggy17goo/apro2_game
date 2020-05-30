package Model.LogicalSkills;

public class Heal extends LogicalSkill {
    public Heal(int distance, int index){
        this.index=index;
        skillName = "Heal";
        this.distance=distance;
        this.value=+10;
        this.range=1;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.Lob;
        rangeType=SkillProperty.PointRange;
    }
}
