package Client.GUI;


import Client.Model.GraphicalHeroes.HeroType;

public class Move {
    private int skillIndex;
    private HeroType hero;
    private int y,x;
    public Move(HeroType heroType, int skillIndex, int y, int x){
        this.hero=heroType;
        this.skillIndex=skillIndex;
        this.y=y;
        this.x=x;
    }

}

