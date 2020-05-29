package Model;

import Model.LogicalHeros.LogicalHero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogicalPlayer implements Serializable {
    private String nick;
    private int id;
    private List<LogicalHero> heroesList = new ArrayList<>();
    public String getNick(){
        return this.nick;
    }
    public void setNick(String nick){
        this.nick=nick;
    }
    public int getId(){
        return id;
    }
    public void setID(int id){
        this.id=id;
    }
    public LogicalPlayer(String nick){
        this.nick=nick;
        this.generateId();
    }
    public void addHero(LogicalHero hero){
        heroesList.add(hero);
        hero.setOwner(this);
    }
    public int generateId(){
        this.id=this.getNick().hashCode();
        return this.id;
    }

    public void removeHero(LogicalHero hero){
        heroesList.remove(hero);
    }

    @Override
    public boolean equals(Object otherPlayer) {
        LogicalPlayer other = (LogicalPlayer) otherPlayer;
        if(this.id == other.id)
            return true;
        else
            return false;
    }

    public boolean hasAliveHeroes(){
        boolean hasAliveHeroes=false;
        for (LogicalHero hero: heroesList) {
            if(hero.isAlive()) {
                hasAliveHeroes = true;
                break;
            }
        }
        return hasAliveHeroes;
    }

    public List<LogicalHero> getHeroesList() {
        return heroesList;
    }

    @Override
    public String toString() {
        return "LogicalPlayer{" +
                nick + "\n" +
                heroesList.get(0) + "\n"+
                heroesList.get(1) + "\n"+
                heroesList.get(2) + "\n"+
                heroesList.get(3) + "\n"+
                '}';
    }
}
