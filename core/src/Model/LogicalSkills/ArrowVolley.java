package Model.LogicalSkills;


public class ArrowVolley extends LogicalSkill {
	public ArrowVolley(int index) {
		this.index=index;
		skillName = "Arrow volley";
		distance = 8;
		value = -10;
		range = 3;
		afterAttack= SkillProperty.StayOnSpot;
		useDistance= SkillProperty.Lob;
		rangeType= SkillProperty.FloodRange;
	}
}
