package Client.Model.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Entity extends Image {
    protected boolean isFixed=false; // can it be moved by a hero
    protected boolean isVisible=true; // is it visible
    protected boolean isCrossable=true; // can you pass through it and see over/through it
    protected boolean isAttackable=true;
    protected int mapX,mapY;

    //For GUI
    public final static int WIDTH = 152;
    public final static int HEIGHT = 152;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;
    public String imagePath;

    public Entity(){
        super(new Texture("Entity.png"));

        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(STARTING_X,STARTING_Y);
    }
    public Entity(String imagePath){
        super(new Texture(imagePath));

        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(STARTING_X,STARTING_Y);
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

    public void reactOnClick(){
        Action testAction = Actions.moveBy(10,10);
        this.addAction(testAction);
    }
}
