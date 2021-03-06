package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Necromancer extends Hero {

    public Necromancer(int y, int x) {
        super("heroGraphics/Nekromantka.png", x, y);
        this.heroType = HeroType.NECROMANCER;
        health = 30;
        maxHealth = 60;
        isAlive = true;
        weight = 5;
        speed = 15;
        skillsList.add(new Walk(10, skillsList.size(), "heroGraphics/Nekromantka.png"));
        skillsList.add(new Melee(-9, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Necromancy(skillsList.size()));
        skillsList.add(new PlaceTrap(skillsList.size()));
    }


    @Override
    public String toString() {
        return "Na";
    }

}
