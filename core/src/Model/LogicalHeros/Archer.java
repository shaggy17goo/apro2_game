package Model.LogicalHeros;

import Client.GUI.*;

public class Archer extends Hero {

    public Archer(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;

    }
    public Archer() {
        mapY = 4;
        mapX = 3;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;

    }

    @Override
    public String toString(){
        return TUI.ANSI_GREEN + "→)" + TUI.ANSI_RESET;
    }
}
