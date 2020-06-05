package Client.Map;

/**
 * Class after which all walls, traps etc. are inheriting
 */
public abstract class Obstacle extends Entity {
    public Obstacle() {
    }

    public Obstacle(String imagePath, int x, int y) {
        super(imagePath, x, y);
    }

}
