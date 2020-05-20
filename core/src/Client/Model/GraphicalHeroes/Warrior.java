package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Jump;
import Client.Model.GraphicalSkills.Melee;
import Client.Model.GraphicalSkills.Walk;

public class Warrior extends Hero{

    public Warrior(int y, int x) {
        super("warrior2.0.png",x,y);
        /*this.mapY = y;
        this.mapX = x;*/
        this.heroType=HeroType.WARRIOR;
        health = 50;
        maxHealth = 10;
        isAlive = true;
        weight = 20;
        skillsList.add(new Walk(5,skillsList.size()));
        //skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
    }



    @Override
    public String toString(){
        return "Wa";}

}
