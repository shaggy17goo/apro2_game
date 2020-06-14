package Model.LogicalHeros;


import Model.LogicalSkills.*;

import java.io.Serializable;

public class Priest extends LogicalHero implements Serializable {

    public Priest(int y, int x) {
        super();
        this.heroType = HeroType.PRIEST;
        this.mapY = y;
        this.mapX = x;
        health = 20;
        maxHealth = 40;
        isAlive = true;
        weight = 15;
        speed = 13;
        skillsList.add(new Walk(10, skillsList.size()));
        skillsList.add(new Melee(-5, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Heal(10, skillsList.size()));
        skillsList.add(new PlaceWall(skillsList.size()));

    }

    @Override
    public String toString() {
        return "Pr";
    }
}
