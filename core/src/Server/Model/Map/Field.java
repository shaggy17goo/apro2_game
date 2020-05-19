package Server.Model.Map;
import Server.Model.Heros.Hero;

public class Field {

    private int mapX,mapY;
    private int type;
    //private List<Entity> entityList=new ArrayList<>();
    private Hero hero;
    private Obstacle obstacle;
    public Field(int y,int x){
        this.mapX=x;
        this.mapY=y;
    }
    public void addHero(Hero hero){
            this.hero=hero;
    }
    public Hero getHero(){
        return this.hero;
    }
    public void addObstacle(Obstacle obstacle){
        this.obstacle=obstacle;
    }
    public Obstacle getObstacle(){
        return this.obstacle;
    }
    @Override
    public String toString(){ // should be Overridden in all inheriting classes
        if(hero!=null) return hero.toString();
        else if(obstacle!=null) return obstacle.toString();
        else return "__";   // empty field
        //else return "  ";   // empty field
    }

}
