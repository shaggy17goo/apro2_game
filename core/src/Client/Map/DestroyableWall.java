package Client.Map;

import java.util.Random;

public class DestroyableWall extends Obstacle {
    public int durability;
    public DestroyableWall(int y,int x){
        super("fieldGraphics/DesWall.png",x,y);
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
