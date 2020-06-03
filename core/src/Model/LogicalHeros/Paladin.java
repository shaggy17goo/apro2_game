package Model.LogicalHeros;

import Model.LogicalSkills.*;

import java.io.Serializable;

public class Paladin extends LogicalHero implements Serializable {
    public Paladin(int y, int x){
        super();
        this.heroType= HeroType.PALADIN;
        this.mapY = y;
        this.mapX = x;
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight=15;
        speed = 7;
        skillsList.add(new Walk(14,skillsList.size()));
        skillsList.add(new Melee(-15,skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new AreaHeal(skillsList.size()));
        skillsList.add(new Charge(-20,skillsList.size()));

    }
    @Override
    public String toString(){
        return "Pa";
    }
}
