package Client.Model.LogicalEntities;

import Client.GUI.Move;
import Client.Model.*;
import Client.Model.GraphicalHeroes.HeroType;
import Client.Model.GraphicalSkills.Necromancy;
import Client.Model.GraphicalSkills.Walk;
import Client.Model.Map.*;

import java.util.ArrayList;
import java.util.List;

public class LogicalHero {
    protected boolean isFixed = false; // can it be moved by a hero
    protected boolean isVisible = true; // is it visible
    protected boolean isCrossable = true; // can you pass through it and see over/through it
    protected boolean isAttackable = true;
    protected int mapX, mapY;

    protected HeroType heroType;
    protected LogicalPlayer owner;
    //To moga być double - jak wygodniej
    protected int speed;
    protected int attackRange;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;
    protected List<LogicalSkill> logicalSkillsList = new ArrayList<>();


    public LogicalHero(LogicalPlayer owner,HeroType heroType, int mapY, int mapX,int health,boolean isAlive ) {
        this.owner = owner;
        this.heroType = heroType;
        this.mapY = mapY;
        this.mapX = mapX;
        this.health = health;
        this.isAlive = isAlive;
    }
    public void getPropertiesFromHeroType(HeroType heroType){
        switch(heroType){
            case ARCHER:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case NECROMANCER:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case PALADIN:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case PRIEST:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case USZATEK:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case WARRIOR:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
            case WIZARD:{
                this.maxHealth = 5;
                this.isAlive = true;
                this.weight = 5;
                this.logicalSkillsList.add(new LogicalSkill());
                this.logicalSkillsList.add(new LogicalSkill());
                break;
            }
        }
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setCrossable(boolean crossable) {
        isCrossable = crossable;
    }

    public void setAttackable(boolean attackable) {
        isAttackable = attackable;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public List<LogicalSkill> getLogicalSkillsList() {
        return logicalSkillsList;
    }

    public void setLogicalSkillsList(List<LogicalSkill> logicalSkillsList) {
        this.logicalSkillsList = logicalSkillsList;
    }

    public LogicalPlayer getOwner() {
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

    public List<LogicalSkill> getSkillsList() {
        return logicalSkillsList;
    }

    public void setOwner(LogicalPlayer owner) {
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

    public void setSkillsList(List<LogicalSkill> skillsList) {
        this.logicalSkillsList = skillsList;
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

    public void setMapX(int x) {
        this.mapX = x;
    }

    public void setMapY(int y) {
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

