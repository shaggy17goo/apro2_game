package Client.Model.graphicalheros;

import Client.GUI.TUI;
import Client.Model.Skills.Arrow;
import Client.Model.Skills.Jump;
import Client.Model.Skills.Walk;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("archer2.png", x, y);
        //this.mapY = y;
        //this.mapX = x;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;
        skillsList.add(new Walk(5, skillsList.size()));
        skillsList.add(new Arrow(skillsList.size()));
        skillsList.add(new Jump(10, skillsList.size()));

    }

    public Archer() {
        mapY = 4;
        mapX = 3;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;

    }

    @Override
    public String toString() {
        return TUI.ANSI_GREEN + "→) " + TUI.ANSI_RESET + health;
    }
}
