package Client.Model.GraphicalHeroes;

import Client.GUI.*;
import Client.Model.GraphicalSkills.Necromancy;
import Client.Model.GraphicalSkills.Walk;

public class Necromancer extends Hero{

    public Necromancer(int y, int x) {
        super("Nekromantka.png",x,y);
        heroIdentification=HeroType.NECROMANCER;
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
