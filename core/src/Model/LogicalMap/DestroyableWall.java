package Model.LogicalMap;

import java.io.Serializable;

public class DestroyableWall extends Obstacle  implements Serializable {
    public DestroyableWall(int y, int x){
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = true;
    }
    @Override
    public String toString() {
        if (isVisible) {
            return  "##";
        } else {
            return "  ";
        }
    }
}
