package Client.Model.Heros;

import Client.GUI.*;

public class Priest extends Hero{

    public Priest() {
        mapY = 7;
        mapX = 3;
        health = 7;
        maxHealth = 7;
        isAlive = true;
        weight = 1;
    }

    public Priest(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 7;
        maxHealth = 7;
        isAlive = true;
        weight = 1;
    }

    @Override
    public String toString(){
        return "Pr";
    }
}
