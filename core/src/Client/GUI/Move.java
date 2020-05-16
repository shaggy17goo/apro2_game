package Client.GUI;

import Client.Model.*;
import Client.Model.Heros.*;

public class Move {
    private Player player;
    private Hero hero;
    private int skillIndex;
    private int y,x;
    public Move(Player player, Hero hero, int skillIndex, int y, int x){
        this.player=player;
        this.hero=hero;
        this.skillIndex=skillIndex;
        this.y=y;
        this.x=x;
    }

    public Player getPlayer() {
        return player;
    }

    public Hero getHero() {
        return hero;
    }

    public int getSkillIndex() {
        return skillIndex;
    }

    public int getMapY() {
        return y;
    }

    public int getMapX() {
        return x;
    }
}