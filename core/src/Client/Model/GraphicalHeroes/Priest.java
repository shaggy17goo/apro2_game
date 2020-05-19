package Client.Model.GraphicalHeroes;

import Client.Model.GraphicalSkills.Walk;

public class Priest extends Hero{

    public Priest(int y, int x) {
        super("priest.png",x,y);
        //this.mapY = y;
        //this.mapX = x;
        heroIdentification=HeroType.PRIEST;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;
        health = 7;
        weight = 20;
        skillsList.add(new Walk(5,skillsList.size()));
    }

    @Override
    public String toString(){
        return "Pr";
    }
}
