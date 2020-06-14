package Client.Map;

import java.util.Random;

public class Wall extends Obstacle {
    public Wall(int y, int x) {
        super("fieldGraphics/Bricks0.png", x, y);
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }

    /**
     * Randomizing choosing of texture
     */
    private static String texture() {
        Random rand = new Random();
        int i = rand.nextInt(3);
        return "fieldGraphics/Bricks" + i + ".png";


    }

    @Override
    public String toString() {
        if (isVisible) {
            return ("\u2588" + "\u2588");
        } else {
            return "  ";
        }
    }
}
