package Model.LogicalSkills;


import Model.LogicalMap.DestroyableWall;
import Model.LogicalMap.Obstacle;
import Model.LogicalMap.Trap;

public class PlaceTrap extends LogicalSkill {
	public Obstacle trap;
	public PlaceTrap(int index) {
		this.index = index;
		skillName = "Set a trap";
		distance = 1;
		value = -15;
		range = 1;
		afterAttack = SkillProperty.StayOnSpot;
		useDistance = SkillProperty.NoLob;
		rangeType = SkillProperty.PointRange;
	}
	public void setTrap(int y, int x, int damage){
		trap = new Trap(y,x,damage);
	}

}
