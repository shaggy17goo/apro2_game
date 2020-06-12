package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Heal extends Skill {
    public Heal(int distance, int index) {
        super("skillGraphics/heal.png");
        this.distance = distance;
        this.index = index;
        this.value = +10;
        this.range = 1;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Animation for healing
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
        this.addAction(Actions.sequence(
                Actions.moveTo(xt, yt, 0.5f),
                Actions.rotateBy(1080f,.5f),
                Actions.moveBy(0,10f,.5f),
                Actions.removeActor()
        ));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
