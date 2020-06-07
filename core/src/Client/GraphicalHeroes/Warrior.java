package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;

public class Warrior extends Hero {

    public Warrior(int y, int x) {
        super("heroGraphics/warrior.png", x, y);
        this.heroType = HeroType.WARRIOR;
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight = 20;
        speed = 9;
        int currentMeleeStrength = -(20 + 20 * (1 - health / maxHealth));//FIXME This will only be used once and incorrectly

        skillsList.add(new Walk(10, skillsList.size(), "heroGraphics/warrior.png"));
        skillsList.add(new Melee(currentMeleeStrength, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Jump(5, skillsList.size()));
        skillsList.add(new Stomp(skillsList.size()));


    }


    @Override
    public String toString() {
        return "Wa";
    }

}
