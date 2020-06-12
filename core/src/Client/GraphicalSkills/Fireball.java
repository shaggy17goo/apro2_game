package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Fireball extends Skill {
    public Fireball(int index) {
        super("skillGraphics/fireballDirection.png");

        this.index = index;
        distance = 5;
        value = -10;
        range = 3;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.FloodRange;
    }

    /**
     * Animation for fireball
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
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, yt, xt)),
                Actions.parallel(
                        Actions.moveTo(xt, yt, .45f),
                        Actions.sequence(
                                Actions.scaleBy(1.5f,1.5f,.45f)
                        )
                ),
                Actions.removeActor()
        ));
    }
}
