package Model.LogicalHeros;

import Model.LogicalSkills.*;

public class Angel extends LogicalHero {
    public Angel(int y, int x) {
        this.heroType = HeroType.ANGEL;
        health = 20;
        maxHealth = 40;
        isAlive = true;
        weight = 5;
        speed = 50;
        skillsList.add(new Walk(10, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-10, skillsList.size()));
        skillsList.add(new Jump(10, skillsList.size()));
        skillsList.add(new Heal(10, skillsList.size()));
        skillsList.add(new AreaHeal(skillsList.size()));

    }

    @Override
    public String toString() {
        return "An";
    }

}
