package Model.LogicalHeros;

import Model.LogicalSkills.*;

import java.io.Serializable;

public class Cyclope extends LogicalHero implements Serializable {

    public Cyclope(int y, int x) {
        super();
        this.heroType = HeroType.CYCLOPE;
        this.mapY = y;
        this.mapX = x;
        health = 100;
        maxHealth = 200;
        isAlive = true;
        weight = 99;
        speed = 1;
        skillsList.add(new Walk(7, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-40,skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Stomp(skillsList.size()));

    }

    @Override
    public String toString() {
        return "00";
    }
}
