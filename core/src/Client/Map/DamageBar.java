package Client.Map;

import Client.GraphicalHeroes.Hero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

/**
 * Graphical representation of how much hero was damaged
 */
public class DamageBar extends Image {
    public final static int WIDTH = 30;
    public final static int HEIGHT = 32;

    public DamageBar(Hero hero) {
        super(new Texture("heroGraphics/dmgbar.png"));
        int mapX = hero.getMapX();
        int mapY = hero.getMapY();
        this.setOrigin(0, HEIGHT / 2);
        this.setSize(WIDTH, HEIGHT);
        this.setPosition(mapX * 32 + 10 + 2, StrategicGame.HEIGHT - (mapY + 1) * 32 - 10 + 20);
        this.scaleBy(0, -0.5f);
    }
}
