package Client.GraphicalHeroes;

import Client.Map.Entity;

public class DeadHero extends Entity {
    public DeadHero(Hero hero){
        super(imagePath(hero.getImagePath()),hero.getMapX(),hero.getMapY());
    }
    private static String imagePath(String str){
        return str.substring(0,str.length()-4) + "Dead.png";
    }
}
