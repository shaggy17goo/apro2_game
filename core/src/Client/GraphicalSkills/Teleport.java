package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Teleport extends Skill {
	public Teleport(int index){
		super("skillGraphics/arrow.png");
		distance = 20;
		this.index=index;
		afterAttack=SkillProperty.GoToTarget;
		useDistance=SkillProperty.Lob;
		rangeType=SkillProperty.PointRange;
	}
	/**
	 * Animation for firing arrows
	 * @param yh starting coordinate of hero
	 * @param xh starting coordinate of hero
	 * @param yt finishing coordinate of target
	 * @param xt finishing coordinate of target
	 */
	public void teleportTo(int yh, int xh, int yt, int xt){
		GameplayScreen.stage.addActor(this);
		this.setX(xh);
		this.setY(yh);
		this.setRotation(0);
		this.addAction(Actions.sequence(
				Actions.moveTo(xt, yt, 0.5f),
				Actions.removeActor()
		));
	}
}
