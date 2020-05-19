package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.*;


public class Wizard extends Hero {

    public Wizard(int y, int x){
        super("wizard.png",x,y);
        /*this.mapY = y;
        this.mapX = x;*/
        this.heroType=HeroType.WIZARD;
        health = 35;
        maxHealth = 70;
        isAlive = true;
        weight=10;
        skillsList.add(new Walk(10,skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
    }

    @Override
    public String toString(){
        return TUI.ANSI_CYAN + "Wi" + TUI.ANSI_RESET + this.mapX + " " + this.mapY;
    }

}
