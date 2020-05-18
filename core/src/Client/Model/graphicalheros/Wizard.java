package Client.Model.graphicalheros;

import Client.GUI.TUI;
import Client.Model.Skills.Fireball;
import Client.Model.Skills.Melee;
import Client.Model.Skills.Stay;
import Client.Model.Skills.Walk;


public class Wizard extends Hero {

    public Wizard() {
        mapY = 14;
        mapX = 14;
        health = 15;
        maxHealth = 15;
        isAlive = true;
        weight = 10;
        skillsList.add(new Walk(0, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
    }

    public Wizard(int y, int x) {
        super("wizard.png", x, y);
        this.mapY = y;
        this.mapX = x;
        health = 15;
        maxHealth = 15;
        isAlive = true;
        weight = 10;
        skillsList.add(new Walk(10, skillsList.size()));
        skillsList.add(new Stay(skillsList.size()));
        skillsList.add(new Melee(skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
    }

    @Override
    public String toString() {
        return TUI.ANSI_CYAN + "Wi" + TUI.ANSI_RESET + this.mapX + " " + this.mapY;
    }

}
