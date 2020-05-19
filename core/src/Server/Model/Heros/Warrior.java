package Server.Model.Heros;

import Client.GUI.TUI;

public class Warrior extends Hero{

    public Warrior(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 25;
        maxHealth = 25;
        isAlive = true;
        weight = 5;
    }


    @Override
    public String toString() {
        return "Wr";
    }
}
