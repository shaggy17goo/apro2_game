package Client.Model.GraphicalHeroes;
import Client.GUI.Move;
import Client.Model.*;
import Client.Model.GraphicalSkills.*;
import Client.Model.Map.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero extends Entity {
    protected LogicalHero logicalHero;
    protected HeroType type;
    protected Player owner;
    protected int speed;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;
    protected List<Skill> skillsList = new ArrayList<>();

    public Hero(String imagePath,int x,int y){
        super(imagePath,x,y);
        logicalHero = new LogicalHero(mapY, mapX, speed, health, maxHealth, weight, isAlive, new ArrayList<>(skillsList));
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


    public void addSkill(Skill skill){
     this.skillsList.add(skill);
     this.logicalHero.getSkillsList().add(skill);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        this.logicalHero.setOwner(owner);
    }

    public void setHealth(int health) {
        this.health=health;
        this.logicalHero.setHealth(health);
        if(this.health<=0) {
            this.health = 0;
            this.logicalHero.setHealth(0);
        }
        else if(this.health>=maxHealth) {
            this.health = maxHealth;
            this.logicalHero.setHealth(maxHealth);
        }
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        this.logicalHero.setAlive(alive);
    }


    public void reactOnClick(int x,int y){
        // 7,7
        GameEngine.performActions(new Move(this.getOwner(),this,0,y,x));
        float[] coordinates=GameEngine.translateMapToGUI(mapY,mapX);
        Action moveAction = Actions.moveTo(coordinates[0],coordinates[1],0.3f);//moveBy(10,10);
        this.addAction(moveAction);
    }
    @Override
    public void reactOnClick(){
        // 7,7
        reactOnClick(7,7);
    }

}

