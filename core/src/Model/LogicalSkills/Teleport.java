package Model.LogicalSkills;

//import Client.GraphicalSkills.Skill;
//import com.badlogic.gdx.graphics.g3d.Model;

public class Teleport extends LogicalSkill {
	public Teleport(int range,int index) {
		this.index=index;
		this.range = range;
		afterAttack=SkillProperty.GoToTarget;
		useDistance=SkillProperty.Lob;
		rangeType=SkillProperty.PointRange;
	}

}
