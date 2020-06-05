package Model.LogicalMap;

import java.io.Serializable;

/**
 * Wall which is placed by a hero
 */
public class DestroyableWall extends Obstacle implements Serializable {
    public int durability;

    public DestroyableWall(int y, int x) {
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = true;
        durability = 10;
    }

    @Override
    public String toString() {
        if (isVisible) {
            return "##";
        } else {
            return "  ";
        }
    }
}
