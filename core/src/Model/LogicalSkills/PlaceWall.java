package Model.LogicalSkills;


import Model.LogicalMap.DestroyableWall;
import Model.LogicalMap.Obstacle;

public class PlaceWall extends LogicalSkill {
    public Obstacle wall;
    public PlaceWall(int index) {
        this.index = index;
        distance = 2;
        value = 0;
        range = 1;
        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;
    }
    public void placeWall(int y, int x){
        wall = new DestroyableWall(y,x);
    }

}
