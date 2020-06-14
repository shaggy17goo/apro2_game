package Client.Map;

public class Trap extends Obstacle {
    private int type; // what does this trap do
    private boolean wasUsed = false;
    protected int damage; // how much damage does it take
    private boolean immobilize; // does this trap immobilize hero

    public boolean wasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    public Trap(int y, int x, int damage) {
        super("fieldGraphics/trap.png", x, y);
        this.mapX = x;
        this.mapY = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = true;
        this.damage = damage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isImmobilize() {
        return immobilize;
    }

    public void setImmobilize(boolean immobilize) {
        this.immobilize = immobilize;
    }

    @Override
    public String toString() {
        if (isVisible)
            return "XX";
        else
            return "  ";
    }
}
