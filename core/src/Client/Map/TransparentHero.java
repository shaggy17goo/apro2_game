package Client.Map;

import Client.GraphicalHeroes.Hero;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;

/**
 * Class for making graphical effects like highlighting players
 */
public class TransparentHero extends Entity{
    public TransparentHero(String imagePath, int x, int y){
        super(imagePath,x,y);
    }
    public static String transparentTexture(Hero hero){
        String str = hero.getImagePath();
        return str.substring(0,str.length()-4) + "Transparent.png";
    }
}
