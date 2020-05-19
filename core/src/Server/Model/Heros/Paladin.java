package Server.Model.Heros;

import Client.GUI.TUI;
import Server.Model.Skills.Walk;

public class Paladin extends Hero {
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
    public String toString() {
        return "Pa";
    }
}
