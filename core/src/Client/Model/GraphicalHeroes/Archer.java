package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Arrow;
import Client.Model.GraphicalSkills.Jump;
import Client.Model.GraphicalSkills.Walk;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("archer2.png",x,y);
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;
        skillsList.add(new Walk(5,skillsList.size()));
        skillsList.add(new Arrow(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

    }

    @Override
    public String toString(){
        return TUI.ANSI_GREEN + "→) " + TUI.ANSI_RESET + health;
    }
}
