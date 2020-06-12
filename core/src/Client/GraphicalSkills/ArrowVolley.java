package Client.GraphicalSkills;

import Client.CorrelationUtils;
import Client.GameEngine;
import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.List;

import static com.badlogic.gdx.Input.Keys.Y;

public class ArrowVolley extends Skill {
    public ArrowVolley(int index) {
        super("skillGraphics/arrowVolley.png");
        this.index = index;
        distance = 8;
        value = -10;
        range = 3;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.FloodRange;
    }

    /**
     * Animation for firing arrows
     *
     * @param yh coordinate of hero
     * @param xh coordinate of hero
     * @param yt coordinate of target
     * @param xt coordinate of target
     */
    public void fireArrowVolley(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        float d = (float) MathUtils.pythagoreanDistance(yh, xh, yt, xt);
        float z = d/64;
        this.addAction(Actions.sequence(
                Actions.rotateBy((float) MathUtils.getDegreeBetween(yh, xh, yt, xt)),
                Actions.parallel(
                        Actions.moveTo(xt, yt, .4f),
                        Actions.sequence(
                                Actions.scaleBy(z,z,.2f),
                                Actions.scaleBy(-z,-z,.2f)
                        )
                ),
                Actions.removeActor()
        ));


    }

    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        int [] coords = CorrelationUtils.guiToMapConvert(xt,yt);
        List<int[]> dfs = GameEngine.getPointsInRangeDFS(coords[1], coords[0], 1);
        
        for(int[] ints : dfs){
            coords = CorrelationUtils.mapToGuiConvert(ints[1],ints[0]);
            fireArrowVolley(yh, xh, coords[1], coords[0]);
            try {
                Thread.sleep(575);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

