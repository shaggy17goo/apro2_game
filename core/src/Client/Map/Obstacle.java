package Client.Map;

public abstract class Obstacle extends Entity { //add more inheriting classes
    public Obstacle(){}
    public Obstacle(String imagePath,int x,int y){
        super(imagePath,x,y);
    }
}
