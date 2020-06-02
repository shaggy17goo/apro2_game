package Client.GraphicalSkills;

import Client.CorrelationUtils;
import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.List;

import static Client.GameEngine.getPointsInRangeDFS;

public class ArrowVolley extends Skill {
	public ArrowVolley(int index) {
		super("skillGraphics/arrowVolley.png");
		this.index=index;
		distance = 15;
		value = -10;
		range = 3;
		afterAttack=SkillProperty.StayOnSpot;
		useDistance=SkillProperty.NoLob;
		rangeType=SkillProperty.FloodRange;
	}

	/**
	 * Animation for firing arrows
	 * @param yh coordinate of hero
	 * @param xh coordinate of hero
	 * @param yt coordinate of target
	 * @param xt coordinate of target
	 */
	public void fireArrowVolley(int yh, int xh, int yt, int xt){


		/*int i =0;
		List<int[]> toUse;
		int [] fields = CorrelationUtils.guiToMapConvert(xt,yt);
		toUse = getPointsInRangeDFS(fields[1], fields[0], range);
		for (int[] ints : toUse) {
			GameplayScreen.stage.addActor(this);
			this.setX(xh);
			this.setY(yh);
			this.setRotation(0);
			Sound arrowSound= Gdx.audio.newSound(Gdx.files.internal("soundEffects/arrow.wav"));
			arrowSound.play();
			this.addAction(
					Actions.sequence(
						Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, CorrelationUtils.mapToGuiConvert(toUse.get(i)[1], toUse.get(i)[0])[0],CorrelationUtils.mapToGuiConvert(toUse.get(i)[1], toUse.get(i)[0])[1])),
						Actions.moveTo(toUse.get(i)[1], toUse.get(i)[0], 1.5f),
						Actions.removeActor()
					)
			);
			System.out.println(ints);
			i++;
		}*/
	}
}
