package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Walk extends Skill {
    public Walk(int distance, int index, String picture) {
        super(picture);
        this.index = index;
        this.distance = distance;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Animation for walking
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
                Actions.removeActor()
        ));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
