package Client.Model.Skills;

public class Fireball extends Skill {
    public Fireball() {
        distance = 5;
        value = -10;
        range = 3;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.FloodRange;
    }
}
