package Model.LogicalMap;

import java.io.Serializable;

public abstract class Entity  implements Serializable {
    protected boolean isFixed=false; // can it be moved by a hero
    protected boolean isVisible=true; // is it visible
    protected boolean isCrossable=true; // can you pass through it and see over/through it
    protected boolean isAttackable=true;
    protected int mapX,mapY;
    protected int id;


    public void setMapPosition(int mapY, int mapX){
        this.mapY=mapY;
        this.mapX=mapX;
    }
    @Override
    public String toString(){ // na razie, pewnie będzie przysłoniona w każdej klasie dziedziczącej
        return this.getClass().toString();
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public void setX(int x) {
        this.mapX = x;
    }

    public void setY(int y) {
        this.mapY = y;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isCrossable() {
        return isCrossable;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

}
