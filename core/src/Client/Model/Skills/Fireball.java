package Client.Model.Skills;

public class Fireball extends Skill {
    public Fireball(int index) {
        super("LOGO.png");
        this.index=index;
        distance = 5;
        value = -10;
        range = 3;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.FloodRange;
    }
}
