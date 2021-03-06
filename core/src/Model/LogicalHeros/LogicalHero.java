package Model.LogicalHeros;

import Model.LogicalMap.Entity;
import Model.LogicalPlayer;
import Model.LogicalSkills.LogicalSkill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Logical representation of a hero - without any Graphics
 */
public abstract class LogicalHero extends Entity implements Serializable {
    private HeroType type;
    protected LogicalPlayer owner;
    protected int speed;
    protected int attackRange;
    protected int health;
    protected int maxHealth;
    protected int weight;
    protected boolean isAlive;
    protected List<LogicalSkill> skillsList = new ArrayList<>();
    protected HeroType heroType;
    protected int id;

    @Override
    public String toString() {
        return "Logical Hero, hp: " + health;
    }

    public List<String> getStats() {
        List<String> stats = new ArrayList<>();
        stats.add(heroType + "\n STATS:");
        stats.add("  HP:     " + health);
        stats.add("  Max HP: " + maxHealth);
        stats.add("  Weight: " + weight);
        stats.add("  Speed:  " + speed);
        stats.add(" SKILLS:");
        for (LogicalSkill skill : skillsList) {
            stats.add("  " + skill);
        }
        return stats;

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
        return skillsList;
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
        this.health = health;
        if (this.health <= 0) {
            this.health = 0;
        } else if (this.health >= maxHealth)
            this.health = maxHealth;

    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setSkillsList(List<LogicalSkill> skillsList) {
        this.skillsList = skillsList;
    }

    public void generateID() {
        int result;
        switch (heroType) {
            case ARCHER:
                result = 1;
                break;
            case NECROMANCER:
                result = 2;
                break;
            case PALADIN:
                result = 3;
                break;
            case PRIEST:
                result = 4;
                break;
            case WIZARD:
                result = 5;
                break;
            case WARRIOR:
                result = 6;
                break;
            case ANGEL:
                result = 7;
                break;
            case CYCLOPE:
                result = 8;
                break;
            default:
                result = 0;
                break;
        }
        result *= owner.generateId();
        this.id = result;
    }


    public HeroType getType() {
        return type;
    }

    public void setType(HeroType type) {
        this.type = type;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogicalHero hero = (LogicalHero) o;
        if (owner.getId() != hero.owner.getId()) return false;
        if (speed != hero.speed) return false;
        if (attackRange != hero.attackRange) return false;
        if (health != hero.health) return false;
        if (maxHealth != hero.maxHealth) return false;
        if (weight != hero.weight) return false;
        if (isAlive != hero.isAlive) return false;
        if (id != hero.id) return false;
        if (type != hero.type) return false;
        return heroType == hero.heroType;
    }
}

