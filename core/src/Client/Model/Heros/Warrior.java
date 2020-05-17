package Client.Model.Heros;

import Client.GUI.*;
import Client.Model.Skills.Jump;
import Client.Model.Skills.Melee;
import Client.Model.Skills.Walk;

public class Warrior extends Hero{

    public Warrior() {
        mapY = 5;
        mapX = 3;
        health = 25;
        maxHealth = 25;
        isAlive = true;
        weight = 5;
    }

    public Warrior(int y, int x) {
        super("warrior2.0.png",x,y);
        this.mapY = y;
        this.mapX = x;
        health = 25;
        maxHealth = 25;
        isAlive = true;
        weight = 5;
        skillsList.add(new Walk(5,skillsList.size()));
        //skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
    }


    @Override
    public String toString(){
        if(this.isAlive)
            return TUI.ANSI_GREEN + "Wa" + TUI.ANSI_RESET;
        else
            return TUI.ANSI_RED + "Wa" + TUI.ANSI_RESET;
    }
}
