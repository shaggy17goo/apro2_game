package Client.Map;

import Client.GraphicalHeroes.Hero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

/**
 * Graphical representation of how much health does a hero have
 */
public class HealthBar extends Image {
    public final static int WIDTH = 30;
    public final static int HEIGHT = 32;

    public HealthBar(Hero hero) {
        super(new Texture("heroGraphics/healthbar.png"));
        int mapX = hero.getMapX();
        int mapY = hero.getMapY();
        this.setOrigin(0, HEIGHT / 2);
        this.setSize(WIDTH, HEIGHT);
        this.setPosition(mapX * 32 + 10 + 2, StrategicGame.HEIGHT - (mapY + 1) * 32 - 10 + 20);
        int healthPercent = 2 * (hero.getHealth() * 100) / (hero.getMaxHealth());
        this.scaleBy(healthPercent / 100.0f - 1, -0.5f);
        System.out.println(hero.getHeroType() + " "+ hero.getOwner().getNick() + " "+ hero.getHealth()+ " "+ healthPercent);
    }
}
