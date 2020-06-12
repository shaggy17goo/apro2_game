package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
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
    public void healArea(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        this.addAction(Actions.sequence(
                Actions.moveTo(xt, yt, 0.35f),
                Actions.scaleBy(2f,2f),
                Actions.moveBy(0f,32f,.2f),
                Actions.removeActor()
        ));
    }
}
