package Server;

import Client.Model.GraphicalHeroes.Hero;
import Client.Model.Player;

public class Move {
    private int hero;
    private int skillIndex;
    private int y,x;
    public Move(int hero, int skillIndex, int y, int x){
        this.hero=hero;
        this.skillIndex=skillIndex;
        this.y=y;
        this.x=x;
    }

  }
