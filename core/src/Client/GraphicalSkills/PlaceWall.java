package Client.GraphicalSkills;

import Client.Map.Obstacle;
import Client.Screens.GameplayScreen;

public class PlaceWall extends Skill {
    public Obstacle wall;

    public PlaceWall(int index) {
        super("fieldGraphics/DesWall.png");
        this.index = index;
        distance = 1;
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
        GameplayScreen.wallsToPlace.add(new int[]{yt, xt});
    }
}
