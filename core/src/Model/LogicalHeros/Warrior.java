package Model.LogicalHeros;

import Client.GUI.*;

public class Warrior extends Hero{

    public Warrior() {
        mapY = 5;
        mapX = 3;
        health = 25;
        maxHealth = 25;
        isAlive = true;
        weight = 5;
    }

    public Warrior(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 25;
        maxHealth = 25;
        isAlive = true;
        weight = 5;
    }


    @Override
    public String toString(){
        if(this.isAlive)
            return TUI.ANSI_GREEN + "Wa" + TUI.ANSI_RESET;
        else
            return TUI.ANSI_RED + "Wa" + TUI.ANSI_RESET;
    }
}
