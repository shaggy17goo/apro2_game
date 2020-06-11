package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ArrowVolley extends Skill {
    public ArrowVolley(int index) {
        super("skillGraphics/arrowVolley.png");
        this.index = index;
        distance = 8;
        value = -10;
        range = 3;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.FloodRange;
    }

    /**
     * Animation for firing arrows
     *
     * @param yh coordinate of hero
     * @param xh coordinate of hero
     * @param yt coordinate of target
     * @param xt coordinate of target
     */
    public void fireArrowVolley(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        Sound arrowSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/arrow.wav"));
        arrowSound.play();
        //System.out.println(yh+", "+xh+", "+yt+", "+xt);
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, .45f),
                Actions.removeActor()
        ));
    }
}

