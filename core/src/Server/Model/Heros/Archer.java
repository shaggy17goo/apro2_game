package Server.Model.Heros;

import Client.GUI.TUI;

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


    @Override
    public String toString() {
        return "Ar";
    }
}
