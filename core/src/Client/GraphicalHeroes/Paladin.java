package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Paladin extends Hero {
    public Paladin(int y, int x) {
        super("heroGraphics/paladin2.png", x, y);
        this.heroType = HeroType.PALADIN;
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight = 15;
        speed = 7;
        skillsList.add(new Walk(10, skillsList.size(), "heroGraphics/paladin2.png"));
        skillsList.add(new Melee(-15, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new AreaHeal(skillsList.size()));
        skillsList.add(new Charge(-20, skillsList.size()));
    }

    @Override
    public String toString() {
        return "Pa";
    }
}
