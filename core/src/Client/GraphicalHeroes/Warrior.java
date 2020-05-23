package Client.GraphicalHeroes;

import Client.GraphicalSkills.Jump;
import Client.GraphicalSkills.Melee;
import Client.GraphicalSkills.Walk;

public class Warrior extends Hero{

    public Warrior(int y, int x) {
        super("heroGraphics/warrior2.0.png",x,y);
        this.heroType=HeroType.WARRIOR;
        health = 50;
        maxHealth = 10;
        isAlive = true;
        weight = 20;
        speed = 9;
        skillsList.add(new Walk(10,skillsList.size()));
        //skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));
        skillsList.add(new Melee(-10,skillsList.size()));

    }



    @Override
    public String toString(){
        return "Wa";}

}
