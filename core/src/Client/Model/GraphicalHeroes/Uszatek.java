package Client.Model.GraphicalHeroes;

import Client.GUI.TUI;
import Client.Model.GraphicalSkills.Fireball;
import Client.Model.GraphicalSkills.Jump;
import Client.Model.GraphicalSkills.Walk;

public class Uszatek extends Hero{
    public Uszatek(int y, int x) {
        super("paczesny.png",x,y);
        this.scaleBy(2f);
        //this.mapY = y;
        //this.mapX = x;
        this.heroType=HeroType.USZATEK;
        health = 50;
        maxHealth = 10;
        isAlive = true;
        weight = 1;
        skillsList.add(new Walk(10,skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }
    @Override
    public String toString(){
        return TUI.ANSI_GREEN + "()-_-() " + TUI.ANSI_RESET + " " +mapX+" "+ mapY +" " +health;
    }
}
