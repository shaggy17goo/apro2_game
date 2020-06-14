package Client.Map;

/**
 * Class for making graphical effects like highlighting players
 */
public class TransparentEntity extends Entity {
    public TransparentEntity(String imagePath, int x, int y) {
        super(imagePath, x, y);
    }

    public static String transparentEntity(String str) {
        return str.substring(0, str.length() - 4) + "Transparent.png";
    }
}
