package Client.Model.GraphicalSkills;

import Client.Model.GameEngine;
import Client.Model.GraphicalHeroes.Hero;
import Screens.GameplayScreen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import javax.sound.midi.Sequence;

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
        /*int[] mapXY = GameEngine.guiToMapConvert(xt,yt);
        Hero hero = GameEngine.getGameMap().getFieldAt(mapXY[1],mapXY[0]).getHero();
        if(hero != null && !hero.isAlive()) hero.remove();*/
        this.addAction(Actions.sequence(
                Actions.rotateBy((float)GameEngine.getDegreeBetween(yh, xh, yt, xt)),
                Actions.moveTo(xt, yt, 1),
                Actions.removeActor()
        ));
    }
}
