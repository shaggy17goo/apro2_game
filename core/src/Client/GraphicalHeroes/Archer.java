package Client.GraphicalHeroes;

import Client.GraphicalSkills.Arrow;
import Client.GraphicalSkills.Jump;
import Client.GraphicalSkills.Walk;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("heroGraphics/archer2.png",x,y);
        this.heroType=HeroType.ARCHER;
        health = 30;
        maxHealth = 60;
        isAlive = true;
        weight = 10;
        speed = 20;
        skillsList.add(new Walk(10,skillsList.size(),"heroGraphics/archer2.png"));
        skillsList.add(new Arrow(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }

    @Override
    public String toString(){
        return "→)";}

}
