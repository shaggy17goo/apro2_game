package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Walk;

public class Paladin extends Hero {
    public Paladin(int y, int x){
        super("paladin.png",x,y);
        /*this.mapY = y;
        this.mapX = x;*/
        health = 50;
        maxHealth = 100;
        isAlive = true;
        weight=15;
        skillsList.add(new Walk(5,skillsList.size()));
    }
    @Override
    public String toString(){
        if(this.isAlive)
        return TUI.ANSI_YELLOW + "Pa" + TUI.ANSI_RESET;
        else
            return TUI.ANSI_RED + "Pa" + TUI.ANSI_RESET;
    }
}
