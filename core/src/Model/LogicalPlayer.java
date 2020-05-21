package Model;

import Model.LogicalHeros.LogicalHero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogicalPlayer implements Serializable {
    private String nick;
    private int id;
    private List<LogicalHero> herosList= new ArrayList<>();
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
        herosList.add(hero);
        hero.setOwner(this);
    }
    public int generateId(){
        this.id=this.getNick().hashCode();
        return this.id;
    }

    public void removeHero(LogicalHero hero){
        herosList.remove(hero);
    }

    @Override
    public String toString() {
        return "LogicalPlayer{" +
                "nick='" + nick  + ", id=" + id + '}';
    }
}
