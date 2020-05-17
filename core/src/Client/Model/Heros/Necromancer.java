package Client.Model.Heros;

import Client.GUI.*;
import Client.Model.Skills.Necromancy;
import Client.Model.Skills.Walk;

public class Necromancer extends Hero{

    public Necromancer() {
        mapY = 7;
        mapX = 3;
        health = 5;
        maxHealth = 5;
        isAlive = true;
        weight = 5;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Necromancy(skillsList.size()));
    }

    public Necromancer(int y, int x) {
        super("Nekromantka.png",x,y);
        //this.mapY = y;
        //this.mapX = x;
        health = 5;
        maxHealth = 5;
        isAlive = true;
        weight = 5;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Necromancy(skillsList.size()));
    }

    @Override
    public String toString(){
        return TUI.ANSI_PURPLE + "Ne" + TUI.ANSI_RESET;
    }
}
