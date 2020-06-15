package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class AreaHeal extends Skill {
    public AreaHeal(int index) {
        super("skillGraphics/heal.png");

        this.index = index;
        distance = 5;
        value = 5;
        range = 3;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.FloodRange;
    }

    /**
     * Animation for healing
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
        Sound swordSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/Agile.wav"));
        swordSound.play();
        this.addAction(Actions.sequence(
                Actions.moveTo(xt, yt, 0.5f),
                Actions.scaleBy(2f,2f,.3f),
                Actions.moveBy(0f,32f,.5f),
                Actions.removeActor()
        ));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
