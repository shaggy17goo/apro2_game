package Model.LogicalHeros;


import Model.LogicalSkills.Necromancy;
import Model.LogicalSkills.Walk;

public class Necromancer extends Hero{

    public Necromancer() {
        mapY = 7;
        mapX = 3;
        health = 5;
        maxHealth = 5;
        isAlive = true;
        weight = 5;
        skillsList.add(new Walk(5));
        skillsList.add(new Necromancy());
    }

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
    public String toString(){
        return TUI.ANSI_PURPLE + "Ne" + TUI.ANSI_RESET;
    }
}
