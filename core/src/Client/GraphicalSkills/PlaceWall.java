package Client.GraphicalSkills;

import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class PlaceWall extends Skill{
    public PlaceWall(int index) {
        super("fieldGraphics/Brics2.png");
        this.index = index;
        distance = 2;
        value = 0;
        range = 1;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Graphically place the wall
     */
    public void placeWall(int yh, int xh) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
    }
}
