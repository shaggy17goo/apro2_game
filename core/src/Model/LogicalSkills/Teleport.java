package Model.LogicalSkills;

import Client.GraphicalSkills.Skill;
import com.badlogic.gdx.graphics.g3d.Model;

public class Teleport extends LogicalSkill {
	public Teleport(int index) {
		distance = 20;
		this.index = index;
		afterAttack = Model.LogicalSkills.SkillProperty.GoToTarget;
		useDistance = Model.LogicalSkills.SkillProperty.Lob;
		rangeType = SkillProperty.PointRange;
	}
}