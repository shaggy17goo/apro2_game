package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Arrow extends Skill{
    public Arrow(int index) {
        super("skillGraphics/arrow.png");
        this.index=index;
        distance = 20;
        value = -20;
        range = 1;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.PointRange;
    }

    /**
     * Animation for firing arrows
     * @param yh coordinate of hero
     * @param xh coordinate of hero
     * @param yt coordinate of target
     * @param xt coordinate of target
     */
    public void fireArrow(int yh, int xh, int yt, int xt){
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        Sound arrowSound= Gdx.audio.newSound(Gdx.files.internal("soundEffects/arrow.wav"));
        arrowSound.play();
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, 0.5f),
                Actions.removeActor()
        ));
    }
}
