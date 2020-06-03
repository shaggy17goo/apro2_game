package Model.LogicalSkills;

public class Step extends LogicalSkill {
	public Step(int index) {
		this.index=index;
		skillName = "Earthquake";
		distance = 8;
		value = -8;
		range = 3;
		afterAttack=SkillProperty.GoToTarget;
		useDistance=SkillProperty.Flood;
		rangeType=SkillProperty.FloodRange;
	}
}
