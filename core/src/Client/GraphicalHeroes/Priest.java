package Client.GraphicalHeroes;

import Client.GraphicalSkills.Walk;

public class Priest extends Hero{

    public Priest(int y, int x) {
        super("priest.png",x,y);
        //this.mapY = y;
        //this.mapX = x;
        this.heroType=HeroType.PRIEST;
        health = 20;
        maxHealth = 40;
        isAlive = true;
        weight = 15;
        skillsList.add(new Walk(5,skillsList.size()));
    }

    @Override
    public String toString(){
        return "Pr";
    }
}
