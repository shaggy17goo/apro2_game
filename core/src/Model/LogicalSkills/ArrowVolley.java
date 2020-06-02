package Model.LogicalSkills;


public class ArrowVolley extends LogicalSkill {
	public ArrowVolley(int index) {
		this.index=index;
		distance = 15;
		value = -10;
		range = 3;
		afterAttack= SkillProperty.StayOnSpot;
		useDistance= SkillProperty.NoLob;
		rangeType= SkillProperty.FloodRange;
	}
}
