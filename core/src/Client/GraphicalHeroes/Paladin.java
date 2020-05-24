package Client.GraphicalHeroes;

import Client.GraphicalSkills.Walk;

public class Paladin extends Hero {
    public Paladin(int y, int x){
        super("heroGraphics/paladin2.png",x,y);
        this.heroType=HeroType.PALADIN;
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight=15;
        speed = 7;
        skillsList.add(new Walk(10,skillsList.size()));

    }

    @Override
    public String toString(){
        return "Pa";}

}
