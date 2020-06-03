package Model.LogicalHeros;


import Model.LogicalSkills.*;

import java.io.Serializable;

public class Wizard extends LogicalHero  implements Serializable {

    public Wizard(int y, int x){
        super();
        this.heroType= HeroType.WIZARD;
        this.mapY = y;
        this.mapX = x;
        health = 35;
        maxHealth = 70;
        isAlive = true;
        weight = 10;
        speed = 12;
        skillsList.add(new Walk(10,skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-10,skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Teleport(20,skillsList.size()));
        skillsList.add(new Ambush(skillsList.size()));

    }

    @Override
    public String toString(){
        return "Wi";
    }

}
