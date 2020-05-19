package Server.Model.Heros;

public class Priest extends Hero{


    public Priest(int y, int x) {
        this.mapY = y;
        this.mapX = x;
        health = 7;
        maxHealth = 7;
        isAlive = true;
        weight = 1;
    }

    @Override
    public String toString(){
        return "Pr";
    }
}
