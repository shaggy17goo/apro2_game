package Client.Map;

import Client.GraphicalHeroes.Hero;


public class HealthBar extends Entity{
    public HealthBar(Hero hero){
        super("heroGraphics/healthbar.png",(int)hero.getX(),(int)hero.getY());
    }
}
