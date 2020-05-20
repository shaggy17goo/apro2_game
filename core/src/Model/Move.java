package Model;


import Model.GraphicalHeroes.Hero;
import Model.GraphicalSkills.Skill;

import java.io.Serializable;

public class Move implements Serializable {
    private int mapY,mapX;
    private Hero hero;
    private Skill skill;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Move(Player player, Hero hero, Skill skill, int mapY, int mapX){
        this.player=player;
        this.hero=hero;
        this.skill=skill;
        this.mapY=mapY;
        this.mapX=mapX;
    }
    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}

