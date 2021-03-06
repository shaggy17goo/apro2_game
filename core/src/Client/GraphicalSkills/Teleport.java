package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Teleport extends Skill {
    public Teleport(int distance, int index) {
        super("skillGraphics/teleport.png");
        this.index = index;
        this.distance = distance;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Animation for teleporting
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
        Sound teleportSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/teleport.wav"));
        teleportSound.play();
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
