package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Necromancy extends Skill {
    public Necromancy(int index) {
        super("skillGraphics/Necromancy.png");
        this.distance = 10;
        this.range = 1;
        this.index = index;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Animation for resurrecting
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
        Sound reviveSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/revive.wav"));
        reviveSound.play();
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, 0.5f),
                Actions.removeActor()
        ));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}