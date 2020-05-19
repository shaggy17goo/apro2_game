package Client.GUI;


import Client.Model.GraphicalHeroes.Hero;
import Client.Model.GraphicalSkills.Skill;

public class Move {
    private int mapY,mapX;
    private Hero hero;
    private Skill skill;
    public Move(Hero hero, Skill skill, int mapY, int mapX){
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

