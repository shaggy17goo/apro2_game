package Model.LogicalMap;

import Client.GUI.*;

public class Wall extends Obstacle {
    public Wall(int y,int x){
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
            return TUI.ANSI_WHITE + "▒▒" + TUI.ANSI_RESET;
        } else {
            return "  ";
        }
    }
}
