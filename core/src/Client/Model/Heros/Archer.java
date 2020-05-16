package Client.Model.Heros;

import Client.GUI.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Archer extends Hero {
    public Archer(int y, int x) {
        super("Hero.png");
        this.mapY = y;
        this.mapX = x;
        health = 2;
        maxHealth = 2;
        attackRange = 3;
        isAlive = true;
        weight = 2;

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
        return TUI.ANSI_GREEN + "→)" + TUI.ANSI_RESET;
    }
}
