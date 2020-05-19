package Client.Model;

import Client.Model.GraphicalSkills.Skill;

import java.util.ArrayList;
import java.util.List;

public class LogicalHero {
    protected int mapY;
    protected int mapX;
    protected Player owner;
    protected int speed;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;
    protected List<Skill> skillsList = new ArrayList<>(); //słowo "skill" mi nie odpowiada


    public LogicalHero(int mapY, int MapX, int speed, int health, int maxHealth, int weight, boolean isAlive, List<Skill> skillsList) {
        this.mapY=mapY;
        this.mapX=mapX;
        this.speed = speed;
        this.health = health;
        this.maxHealth = maxHealth;
        this.weight = weight;
        this.isAlive = isAlive;
        this.skillsList = skillsList;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<Skill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }


}
