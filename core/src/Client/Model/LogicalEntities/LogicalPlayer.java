package Client.Model.LogicalEntities;

import Client.Model.GraphicalHeroes.*;

import java.util.ArrayList;
import java.util.List;

public class LogicalPlayer {
    private String nick;
    private int id;
    private List<LogicalHero> logicalHeroList= new ArrayList<>();
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

    public LogicalPlayer(String nick,int id){

    }
    public void addLogicalHero(LogicalHero logicalHero){
        logicalHeroList.add(logicalHero);
        logicalHero.setOwner(this);
    }

    public void removeHero(Hero hero){
        logicalHeroList.remove(hero);
    }

}
