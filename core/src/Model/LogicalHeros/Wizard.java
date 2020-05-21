package Model.LogicalHeros;

import Client.GUI.*;
import Client.Model.Skills.*;


public class Wizard extends Hero {

    public Wizard(){
        mapY=14;
        mapX=14;
        health = 15;
        maxHealth = 15;
        isAlive = true;
        weight=10;
        skillsList.add(new Walk(1));
        skillsList.add(new Stay());
        skillsList.add(new Melee());
        skillsList.add(new Fireball());
    }

    public Wizard(int y, int x){
        this.mapY = y;
        this.mapX = x;
        health = 15;
        maxHealth = 15;
        isAlive = true;
        weight=10;
        skillsList.add(new Walk(3));
        skillsList.add(new Stay());
        skillsList.add(new Melee());
        skillsList.add(new Fireball());
    }

    @Override
    public String toString(){
        return TUI.ANSI_CYAN + "Wi" + TUI.ANSI_RESET;
    }

}
