package Client.Model.Heros;
import Client.GUI.Move;
import Client.Model.*;
import Client.Model.Skills.*;
import Client.Model.Map.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero extends Entity {
    private HeroType type;
    protected Player owner;
    //To moga być double - jak wygodniej
    protected int speed;
    protected int attackRange;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;
    protected List<Skill> skillsList = new ArrayList<>(); //słowo "skill" mi nie odpowiada

    public Hero(){}
    public Hero(String imagePath,int x,int y){
        super(imagePath,x,y);
    }                                                            // how about "super power"
    @Override
    public String toString() {
        /*switch (type) {
            case WARRIOR : return "Wr";
            case WIZARD : return "Zz";
            case ARCHER : return "→)";
            case PRIEST : return "++";
            case NECROMANCER : return "¿?";
            case PALADIN : return "┼┼";
        }*/
        return "He";//super.toString();
    }

    public Player getOwner() {
        return owner;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<Skill> getSkillsList() {
        return skillsList;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setHealth(int health) {
        this.health=health;
        if(this.health<=0) {
            this.health = 0;
        }
        else if(this.health>=maxHealth)
            this.health = maxHealth;

    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setSkillsList(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }
    public void reactOnClick(int x,int y){
        // 7,7
        GameEngine.performActions(new Move(this.getOwner(),this,0,y,x));
        float[] coordinates=GameEngine.translateMapToGUI(mapY,mapX);
        Action moveAction = Actions.moveTo(coordinates[0],coordinates[1],1);//moveBy(10,10);
        this.addAction(moveAction);
    }
    @Override
    public void reactOnClick(){
        // 7,7
        reactOnClick(7,7);
    }

}

