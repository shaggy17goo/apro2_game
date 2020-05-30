package Client.GraphicalSkills;

import Client.Map.DestroyableWall;
import Client.Map.Obstacle;
import Client.MathUtils;
import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class PlaceWall extends Skill{
    public Obstacle wall;
    public PlaceWall(int index) {
        super("fieldGraphics/Bricks2.png");
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
    public void addWallToList(int yt, int xt) {
       GameplayScreen.wallsToPlace.add(new int[]{yt,xt});
    }
}
