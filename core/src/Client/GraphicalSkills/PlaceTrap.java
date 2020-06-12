package Client.GraphicalSkills;

import Client.Map.Obstacle;
import Client.Screens.GameplayScreen;

public class PlaceTrap extends Skill {
    public Obstacle trap;

    public PlaceTrap(int index) {
        super("fieldGraphics/trap.png");
        this.index = index;
        distance = 1;
        value = -15;
        range = 1;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;
    }

    /**
     * Graphically place the trap
     */
    public void putTrap(int yt, int xt, int damage) {
        GameplayScreen.trapsToPlace.add(new int[]{yt, xt, damage});
    }

    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        //ignore
    }
}
