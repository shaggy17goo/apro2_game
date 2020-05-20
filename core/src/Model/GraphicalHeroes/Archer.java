package Model.GraphicalHeroes;

import Model.GraphicalSkills.Arrow;
import Model.GraphicalSkills.Jump;
import Model.GraphicalSkills.Walk;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("archer2.png",x,y);
        this.heroType=HeroType.ARCHER;
        health = 30;
        maxHealth = 60;
        isAlive = true;
        weight = 10;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Arrow(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }

    @Override
    public String toString(){
        return "→)";}

}
