package Model.LogicalHeros;

import Model.LogicalSkills.Walk;

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
        skillsList.add(new Walk(5,skillsList.size()));

    }
    @Override
    public String toString(){
        return "Pa";
    }
}
