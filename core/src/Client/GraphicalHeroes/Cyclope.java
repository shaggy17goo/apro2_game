package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Cyclope extends Hero {
    public Cyclope(int y, int x) {
        super("heroGraphics/uszatek.png", x, y);

        this.heroType = HeroType.CYCLOPE;
        this.mapY = y;
        this.mapX = x;
        health = 100;
        maxHealth = 200;
        isAlive = true;
        weight = 99;
        speed = 1;
        skillsList.add(new Walk(7, skillsList.size(), "heroGraphics/uszatek.png"));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-40, skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Stomp(skillsList.size()));

    }

    @Override
    public String toString() {
        return "Uc";
    }

}

