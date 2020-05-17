package Client.Model.Heros;

import Client.GUI.*;
import Client.Model.Skills.Fireball;
import Client.Model.Skills.Jump;
import Client.Model.Skills.Walk;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("archer.png",x,y);
        //this.mapY = y;
        //this.mapX = x;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;
        skillsList.add(new Walk(1,skillsList.size()));
        skillsList.add(new Fireball(skillsList.size()));
        skillsList.add(new Jump(5,skillsList.size()));

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
    public String toString(){
        return TUI.ANSI_GREEN + "→) " + TUI.ANSI_RESET + health;
    }
}
