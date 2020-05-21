package Model.LogicalHeros;


import Model.LogicalSkills.Jump;
import Model.LogicalSkills.Melee;
import Model.LogicalSkills.Walk;

import java.io.Serializable;

public class Warrior extends LogicalHero implements Serializable {

    public Warrior(int y, int x) {
        super();
        this.heroType= HeroType.WARRIOR;
        this.mapY = y;
        this.mapX = x;
        health = 50;
        maxHealth = 10;
        isAlive = true;
        weight = 20;
        speed = 9;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));

    }


    @Override
    public String toString(){
            return "Wa";
    }
}
