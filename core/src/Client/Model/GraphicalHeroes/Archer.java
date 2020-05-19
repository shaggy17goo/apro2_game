package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Arrow;
import Client.Model.GraphicalSkills.Jump;
import Client.Model.GraphicalSkills.Walk;
import Client.Model.LogicalHero;

public class Archer extends Hero {

    public Archer(int y, int x) {
        super("archer2.png", x, y);
        health = 2;
        maxHealth = 2;
        isAlive = true;
        weight = 2;
        addSkill(new Walk(5, skillsList.size()));
        addSkill(new Arrow(skillsList.size()));
        addSkill(new Jump(5, skillsList.size()));
    }

    @Override
    public String toString() {
        return TUI.ANSI_GREEN + "→) " + TUI.ANSI_RESET + health;
    }
}
