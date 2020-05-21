package Client.GraphicalSkills;

import Client.GameEngine;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Arrow extends Skill{
    public Arrow(int index) {
        super("arrow.png");
        this.index=index;
        distance = 15;
        value = -20;
        range = 1;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.PointRange;
    }
    public void fireArrow(int yh, int xh, int yt, int xt){
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) GameEngine.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, 0.5f),
                Actions.removeActor()
        ));
    }
}
