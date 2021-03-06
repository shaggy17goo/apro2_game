package Model.LogicalMap;

import java.io.Serializable;

public class Wall extends Obstacle implements Serializable {
    public Wall(int y, int x) {
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
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
