package Client.GraphicalHeroes;
import Client.GameEngine;
import Client.GraphicalSkills.*;
import Client.Map.*;
import Client.Player;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero extends Entity  {
    private HeroType type;
    protected Player owner;
    //To moga być double - jak wygodniej
    protected int speed;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;

    protected int heroIndex;
    protected List<Skill> skillsList = new ArrayList<>();
    protected HeroType heroType;
    protected int id;

    public HeroType getType() {
        return type;
    }

    public void setType(HeroType type) {
        this.type = type;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeroIndex() {
        return heroIndex;
    }

    public void setHeroIndex(int heroIndex) {
        this.heroIndex = heroIndex;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public Hero(){}
    public Hero(String imagePath,int x,int y){
        super(imagePath,x,y);
        GameEngine.graphHeroList.add(this);
        this.heroIndex = GameEngine.graphHeroList.indexOf(this);
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

    public void setOwner(Player owner) {
        this.owner = owner;
        owner.addHero(this);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
        //GameEngine.performActions(new Move(this.heroIdentification,0,y,x));
        float[] coordinates=GameEngine.translateMapToGUI(mapY,mapX);
        Action moveAction = Actions.moveTo(coordinates[0],coordinates[1],0.3f);//moveBy(10,10);
        this.addAction(moveAction);
    }
    @Override
    public void reactOnClick(){
        // 7,7
        reactOnClick(7,7);
    }

    public int generateID(){
        int result;
        switch(heroType){
            case ARCHER: result = 1; break;
            case NECROMANCER: result = 2; break;
            case PALADIN: result = 3; break;
            case PRIEST: result = 4; break;
            case WIZARD: result = 5; break;
            case WARRIOR: result = 6; break;
            case USZATEK: result = 7; break;
            default: result=0; break;
        }
        result*=owner.generateId();
        return result;
    }
    public boolean equalToLogical(Model.LogicalHeros.LogicalHero other){
        return this.getId() == other.getId();
    }

}

