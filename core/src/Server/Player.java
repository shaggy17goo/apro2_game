package Server;


import Server.Model.Heros.Hero;

import java.util.ArrayList;
import java.util.List;

public class Player {
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

    public void addHero(Hero hero){
        herosList.add(hero);
        hero.setOwner(this);
    }

    public void removeHero(Hero hero){
        herosList.remove(hero);
    }

}
