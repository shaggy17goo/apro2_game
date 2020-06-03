package Model.LogicalSkills;

public class AreaHeal extends LogicalSkill {
	public AreaHeal(int index) {
		this.index=index;
		skillName = "Area heal";
		distance = 5;
		value = 5;
		range = 3;
		afterAttack=SkillProperty.StayOnSpot;
		useDistance=SkillProperty.NoLob;
		rangeType=SkillProperty.FloodRange;
	}
}
