package Client.Model.Map;

import Client.GUI.Move;
import Client.Model.GameEngine;
import Client.Model.Heros.Hero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.StrategicGame;

public abstract class Entity extends Image {
    protected boolean isFixed=false; // can it be moved by a hero
    protected boolean isVisible=true; // is it visible
    protected boolean isCrossable=true; // can you pass through it and see over/through it
    protected boolean isAttackable=true;
    protected int mapX,mapY;

    //For GUI
    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;
    public String imagePath;

    public Entity(){
        super(new Texture("Entity.png"));

        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(STARTING_X,STARTING_Y);
    }
    public Entity(String imagePath,int x,int y){
        super(new Texture(imagePath));
        this.mapX=x;
        this.mapY=y;

        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(mapX*WIDTH+10, StrategicGame.HEIGHT-(mapY+1)*HEIGHT-10);
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
        // 7,7
        //if(this.getClass().equals(Hero.class))
        //GameEngine.performActions(new Move(((Hero)this).getOwner(),(Hero)this,0,7,7));
        //Action testAction = Actions.moveTo();//moveBy(10,10);
        //this.addAction(testAction);
    }
}
