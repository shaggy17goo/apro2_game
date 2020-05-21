package Client;

import Client.GraphicalHeroes.*;
import Model.LogicalPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String nick;
    private int id;
    private List<Hero> herosList= new ArrayList<>();
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
    public Player(String nick){
        this.nick=nick;
        this.generateId();
    }
    public void addHero(Hero hero){
        herosList.add(hero);
        hero.setOwner(this);
    }
    public int generateId(){
        this.id=this.getNick().hashCode();
        return this.id;
    }

    public void removeHero(Hero hero){
        herosList.remove(hero);
    }

    public boolean equalToLogicalPlayer(LogicalPlayer other){
        return this.getId() == other.getId();
    }

}
