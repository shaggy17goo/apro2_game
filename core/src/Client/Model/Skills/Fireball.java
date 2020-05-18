package Client.Model.Skills;

import Client.Model.GameEngine;
import Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Fireball extends Skill {
    public Fireball(int index) {
        super("fireballDirection.png");

        this.index=index;
        distance = 5;
        value = -10;
        range = 3;
        afterAttack=SkillProperty.StayOnSpot;
        useDistance=SkillProperty.NoLob;
        rangeType=SkillProperty.FloodRange;
    }
    public void throwFireball(int yh, int xh, int yt, int xt){
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        this.addAction(Actions.sequence(
                Actions.rotateBy((float)GameEngine.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, 1),
                Actions.removeActor()
        ));
    }
}
