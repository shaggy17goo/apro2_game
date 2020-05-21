package Model.LogicalHeros;


import Model.LogicalSkills.Fireball;
import Model.LogicalSkills.Melee;
import Model.LogicalSkills.Stay;
import Model.LogicalSkills.Walk;

public class Wizard extends Hero {

    public Wizard(int y, int x){
        super();
        this.heroType= HeroType.WIZARD;
        this.mapY = y;
        this.mapX = x;
        health = 35;
        maxHealth = 70;
        isAlive = true;
        weight=10;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
    }

    @Override
    public String toString(){
        return "Wi";
    }

}
