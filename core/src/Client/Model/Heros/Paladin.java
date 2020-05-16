package Client.Model.Heros;

import Client.GUI.*;
import Client.Model.Skills.Walk;

public class Paladin extends Hero {
    public Paladin(){
        mapY=3;
        mapX=5;
        health = 3;
        maxHealth = 3;
        isAlive = true;
        weight=3;
    }
    public Paladin(int y, int x){
        this.mapY = y;
        this.mapX = x;
        health = 15;
        maxHealth = 40;
        isAlive = true;
        weight=3;
        skillsList.add(new Walk(5));
    }
    @Override
    public String toString(){
        if(this.isAlive)
        return TUI.ANSI_YELLOW + "Pa" + TUI.ANSI_RESET;
        else
            return TUI.ANSI_RED + "Pa" + TUI.ANSI_RESET;
    }
}
