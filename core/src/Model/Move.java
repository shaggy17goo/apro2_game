package Model;




import Client.Player;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalSkills.LogicalSkill;

import java.io.Serializable;

public class Move implements Serializable {
    private int mapY,mapX;
    private LogicalHero hero;
    private LogicalSkill skill;
    private LogicalPlayer player;

    public LogicalPlayer getPlayer() {
        return player;
    }

    public void setPlayer(Player LogicalPlayer) {
        this.player = player;
    }

    public Move(LogicalPlayer player, LogicalHero hero, LogicalSkill skill, int mapY, int mapX){
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

    public LogicalHero getHero() {
        return hero;
    }

    public void setHero(LogicalHero hero) {
        this.hero = hero;
    }

    public LogicalSkill getSkill() {
        return skill;
    }

    public void setSkill(LogicalSkill skill) {
        this.skill = skill;
    }
}

