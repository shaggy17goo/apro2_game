package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Stomp extends Skill {
    public Stomp(int index) {
        super("skillGraphics/step.png");

        this.index = index;
        distance = 8;
        value = -8;
        range = 3;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.AreaRange;
    }

    /**
     * Animation for firing arrows
     *
     * @param yh coordinate of hero
     * @param xh coordinate of hero
     * @param yt coordinate of target
     * @param xt coordinate of target
     */
    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        Sound stompSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/stomp.wav"));
        stompSound.play();
        this.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.moveTo(xt, yt, .5f),
                        Actions.sequence(
                                Actions.rotateBy(-45,.1f),
                                Actions.rotateBy(90,.1f),
                                Actions.rotateBy(-90,.1f),
                                Actions.rotateBy(90,.1f),
                                Actions.rotateBy(-45,.1f)
                        )
                ),
                Actions.removeActor()
        ));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
