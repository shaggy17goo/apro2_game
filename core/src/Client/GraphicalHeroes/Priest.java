package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Priest extends Hero {

    public Priest(int y, int x) {
        super("heroGraphics/priest.png", x, y);
        this.heroType = HeroType.PRIEST;
        health = 20;
        maxHealth = 40;
        isAlive = true;
        weight = 15;
        speed = 13;
        skillsList.add(new Walk(10, skillsList.size(), "heroGraphics/priest.png"));
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
