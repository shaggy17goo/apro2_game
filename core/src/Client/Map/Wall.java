package Client.Map;

public class Wall extends Obstacle {
    public Wall(int y,int x){
        super("Bricks.png",x,y);
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
    @Override
//    public String toString() { return "Wa"; }
    public String toString() {
        if (isVisible) {
            return "▒▒";
        } else {
            return "  ";
        }
    }
}
