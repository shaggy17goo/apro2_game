package Server.Model.Heros;

import Client.GUI.TUI;
import Server.Model.Skills.*;


public class Wizard extends Hero {

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
    public String toString() {
        return "Wi";
    }

}
