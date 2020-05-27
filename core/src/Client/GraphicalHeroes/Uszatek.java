package Client.GraphicalHeroes;

import Client.GraphicalSkills.Fireball;
import Client.GraphicalSkills.Jump;
import Client.GraphicalSkills.Walk;

public class Uszatek extends Hero{
    public Uszatek(int y, int x) {
        super("heroGraphics/paczesny.png",x,y);
        this.scaleBy(2f);

        this.heroType=HeroType.USZATEK;
        health = 50;
        maxHealth = 10;
        isAlive = true;
        weight = 999;
        speed = 1;
        skillsList.add(new Walk(10,skillsList.size(),"heroGraphics/paczesny.png"));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }

    @Override
    public String toString(){
        return "Uc";
    }

}

