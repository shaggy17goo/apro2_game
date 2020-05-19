package Server.Model.Heros;

import Client.GUI.TUI;
import Server.Model.Skills.*;


public class Necromancer extends Hero{

    public Necromancer(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 5;
        maxHealth = 5;
        isAlive = true;
        weight = 5;
        skillsList.add(new Necromancy());
    }

    @Override
    public String toString() {
        return "Ne";
    }
}
