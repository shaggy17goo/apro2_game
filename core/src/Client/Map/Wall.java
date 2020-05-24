package Client.Map;

public class Wall extends Obstacle {
    public Wall(int y,int x){
        super("fieldGraphics/Bricks.png",x,y);
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
