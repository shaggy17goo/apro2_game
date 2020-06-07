package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Uszatek extends Hero {
    public Uszatek(int y, int x) {
        super("heroGraphics/paczesny.png", x, y);

        this.heroType = HeroType.USZATEK;
        this.mapY = y;
        this.mapX = x;
        health = 100;
        maxHealth = 200;
        isAlive = true;
        weight = 99;
        speed = 1;
        skillsList.add(new Walk(7, skillsList.size(), "heroGraphics/paczesny.png"));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-40, skillsList.size()));
        skillsList.add(new Stomp(skillsList.size()));

    }

    @Override
    public String toString() {
        return "Uc";
    }

}

