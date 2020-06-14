package Client;

import com.badlogic.gdx.math.Vector2;

public class MathUtils {

    /**
     * Calculate distance between two points
     *
     * @return Distance from (x1,y1) to (x2,y2) calculated using pythagorean theorem
     */
    public static double pythagoreanDistance(int y1, int x1, int y2, int x2) {
        return Math.sqrt(Math.pow(y1 - y2, 2) + Math.pow(x1 - x2, 2));
    }

    /**
     * Return degree between 0 and desired target
     */
    public static double getDegreeBetween(int yh, int xh, int yt, int xt) {
        Vector2 rotationVector = new Vector2(xt - xh, yt - yh);
        double beta = rotationVector.angleRad();
        beta *= com.badlogic.gdx.math.MathUtils.radiansToDegrees;
        System.out.println(beta);
        return beta - 90;
    }
}
