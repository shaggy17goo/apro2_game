package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Jump extends Skill {
    public Jump(int distance, int index) {
        super("skillGraphics/jump.png");
        this.index = index;
        this.distance = distance;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Animation for jumping
     *
     * @param yh starting coordinate of hero
     * @param xh starting coordinate of hero
     * @param yt finishing coordinate of target
     * @param xt finishing coordinate of target
     */
    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/jump.wav"));
        jumpSound.play();
        this.addAction(Actions.sequence(
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
