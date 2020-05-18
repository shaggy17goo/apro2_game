package Client.Model.Heros;

import Client.GUI.TUI;
import Client.Model.Skills.Jump;
import Client.Model.Skills.Walk;

public class Pacześniak extends Hero{
    public Pacześniak(int y, int x) {
        super("paczesny.png",x,y);
        //this.mapY = y;
        //this.mapX = x;
        health = 66666;
        maxHealth = 99999;
        attackRange = 99;
        isAlive = true;
        weight = 99999;
        skillsList.add(new Walk(10,skillsList.size()));
        //skillsList.add(new Fireball(skillsList.size()));
        //skillsList.add(new Jump(5,skillsList.size()));

    }
    public Pacześniak() {
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
        return TUI.ANSI_GREEN + "()-_-() " + TUI.ANSI_RESET + " " +mapX+" "+ mapY +" " +health;
    }
}
