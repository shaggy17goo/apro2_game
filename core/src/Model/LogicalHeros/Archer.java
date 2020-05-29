package Model.LogicalHeros;

import Model.LogicalSkills.Arrow;
import Model.LogicalSkills.Jump;
import Model.LogicalSkills.Walk;

import java.io.Serializable;

public class Archer extends LogicalHero implements Serializable {

    public Archer(int y, int x) {
        super();
        this.heroType= HeroType.ARCHER;
        this.mapY = y;
        this.mapX = x;
        health = 30;
        maxHealth = 60;
        isAlive = true;
        weight = 10;
        speed = 20;
        skillsList.add(new Walk(10,skillsList.size()));
        skillsList.add(new Arrow(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }
    @Override
    public String toString(){
        return "→)" ;
    }
}
