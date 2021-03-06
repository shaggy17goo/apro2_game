package Model.LogicalMap;


import Model.LogicalHeros.LogicalHero;

import java.io.Serializable;

public class Field implements Serializable {

    private int mapX, mapY;
    private int type;
    private LogicalHero hero;
    private Obstacle obstacle;

    public Field(int y, int x) {
        this.mapX = x;
        this.mapY = y;
    }

    public void addHero(LogicalHero hero) {
        this.hero = hero;
    }

    public LogicalHero getHero() {
        return this.hero;
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    @Override
    public String toString() { // should be Overridden in all inheriting classes
        if (hero != null) return hero.toString();
        else if (obstacle != null) return obstacle.toString();
        else return "__";   // empty field
    }

}
