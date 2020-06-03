package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Charge extends Skill {
	public Charge(int value, int index) {
		super("skillGraphics/charge.png");
		this.index=index;
		distance = 12;
		this.value = value;
		range = 0;
		afterAttack=SkillProperty.GoToTarget;
		useDistance=SkillProperty.Flood;
		rangeType=SkillProperty.PointRange;
	}
	public void chargeAttack(int yh, int xh, int yt, int xt){
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
