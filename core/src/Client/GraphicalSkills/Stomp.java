package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Stomp extends Skill {
    public Stomp(int index) {
        super("skillGraphics/step.png");//FIXME it doesn't appear even when used

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
    public void useSkill(int yh, int xh, int yt, int xt) {//FIXME doesn't have an appropriate animation
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
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
