package Client.GraphicalSkills;

import Client.Map.Obstacle;
import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Ambush extends Skill{
	public Obstacle trap;
	public Ambush(int index) {
		super("fieldGraphics/trap.png");
		this.index = index;
		distance = 1;
		value = -15;
		range = 1;
		afterAttack = SkillProperty.StayOnSpot;
		useDistance = SkillProperty.NoLob;
		rangeType = SkillProperty.PointRange;
	}

	/**
	 * Graphically place the trap
	 */
	public void putTrap(int yt, int xt,int damage) {
		GameplayScreen.trapsToPlace.add(new int[]{yt,xt,damage});
	}
}
