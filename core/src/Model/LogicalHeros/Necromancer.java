package Model.LogicalHeros;

import Model.LogicalSkills.Necromancy;
import Model.LogicalSkills.Walk;

public class Necromancer extends LogicalHero {

    public Necromancer(int y, int x) {
        super();
        this.heroType= HeroType.NECROMANCER;
        this.mapY = y;
        this.mapX = x;
        health = 30;
        maxHealth = 60;
        isAlive = true;
        weight = 5;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Necromancy(skillsList.size()));
    }

    @Override
    public String toString(){
        return "Ne";
    }
}
