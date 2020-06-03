package Client.GraphicalHeroes;

import Client.GraphicalSkills.*;


public class Wizard extends Hero {

    public Wizard(int y, int x){
        super("heroGraphics/wizard2.png",x,y);
        this.heroType=HeroType.WIZARD;
        health = 35;
        maxHealth = 70;
        isAlive = true;
        weight=10;
        speed = 12;
        skillsList.add(new Walk(10,skillsList.size(),"heroGraphics/wizard2.png"));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(-10,skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Teleport(20,skillsList.size()));
        skillsList.add(new Ambush(skillsList.size()));
    }

    @Override
    public String toString(){
        return "Wi";}

}
