package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Walk;

public class Paladin extends Hero {
    public Paladin(int y, int x){
        super("paladin.png",x,y);
        /*this.mapY = y;
        this.mapX = x;*/
        this.heroType=HeroType.PALADIN;
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight=15;
        skillsList.add(new Walk(5,skillsList.size()));
    }

    @Override
    public String toString(){
        return "Pa";}

}
