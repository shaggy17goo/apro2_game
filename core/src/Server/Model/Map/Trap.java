package Server.Model.Map;

import Client.GUI.TUI;

public class Trap extends Obstacle {
    private int type; // czy zadaje damage, czy może zatrzymuje gracza itd.
    protected int damage;
    private boolean immobilize; // czy zatrzymuje gracza

    public Trap(int y, int x, int damage) {
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
            return TUI.ANSI_WHITE + "XX" + TUI.ANSI_RESET;
        else
            return "  ";
    }
}
