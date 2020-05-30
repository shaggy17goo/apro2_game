package Client.Map;

import java.util.Random;

public class DestroyableWall extends Obstacle {
    public DestroyableWall(int y,int x){
        super("fieldGraphics/Bricks2.png",x,y);
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
            return "##";
        } else {
            return "  ";
        }
    }
}
