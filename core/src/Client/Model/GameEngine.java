package Client.Model;

import Client.GUI.Move;
import Client.Model.Heros.*;
import Client.Model.Map.*;
import Client.Model.Skills.*;
import Screens.GameplayScreen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private static GameMap gameMap;
    private static List<Move> movesQueue = new ArrayList<>();//Queue<Move> movesQueue;
    private static boolean readyToSend=false;
    private static final int movesPerTour=1;

    public GameEngine(int maxY, int maxX) {
        gameMap = new GameMap(maxY, maxX);
    }

    @Override
    public String toString() {
        return gameMap.toString();
    }
    public static void performActions(Move move){
        /*for(int i=0;i<4;i++){
            move=movesQueue.poll();
            useSkill(move.getHero(),move.getSkillIndex(),move.getMapY(),move.getMapX());
        }*/
        if(!validator(move.getHero(),move.getSkillIndex(),move.getMapY(),move.getMapX())) System.out.println("Wyjebałem się");
        else{
            useSkill(move.getHero(),move.getSkillIndex(),move.getMapY(),move.getMapX());
        }


    }
    //Client methods
    public static void sendActionsToServer(){
        // FIXME Send it to server
        System.out.println("Send to server");
        int counter=0;
        for(Move move:movesQueue){
            performActions(move);
            //System.out.println(move);
        }
        //performActions();
    }
    public static float[] translateMapToGUI(int y, int x){
        // FIXME shouldn't be called directly
        return new float[]{x*32+10, StrategicGame.HEIGHT-(y+1)*32-10};
    }
    public static void addActionToQueue(Move move){
        //If move is not valid, show it on Viewer and return
        if(!validator(move.getHero(),move.getSkillIndex(),move.getMapY(),move.getMapX())) {
            // FIXME Show it on Viewer
            System.out.println("Inputted move is not valid");

        } else{//Move is valid
            System.out.println("Move is valid, added to queue");
            movesQueue.add(move);
            if(movesQueue.size()==movesPerTour){
                sendActionsToServer();
                movesQueue.clear();
                //System.out.println(this);
            }
        }

    }

    /**
     * Calculate distance between two points
     *
     * @return Distance from (x1,y1) to (x2,y2) calculated using pythagorean theorem
     */
    public static double pythagoreanDistance(int y1, int x1, int y2, int x2) {
        return Math.sqrt(Math.pow(y1 - y2, 2) + Math.pow(x1 - x2, 2));
    }

    /**
     * Depth first search for possible moves
     *
     * @param y        coordinate to check
     * @param x        coordinate to check
     * @param searched boolean array representing already searched fields
     * @param distance left travel for a hero (derives from Hero.travelDistance)
     */
    private static void dfs(int y, int x, boolean[][] searched, int distance) {
        searched[y][x] = true;

        if (x - 1 >= 0  && distance > 0
                && ((gameMap.getFieldAt(y, x - 1).getObstacle() == null || gameMap.getFieldAt(y, x - 1).getObstacle().isCrossable())))
            dfs(y, x - 1, searched, distance - 1);

        if (y + 1 < gameMap.getMaxY() && distance > 0
                && ((gameMap.getFieldAt(y + 1, x).getObstacle() == null || gameMap.getFieldAt(y + 1, x).getObstacle().isCrossable())))
            dfs(y + 1, x, searched, distance - 1);
        if (x + 1 < gameMap.getMaxX()  && distance > 0
                && ((gameMap.getFieldAt(y, x + 1).getObstacle() == null || gameMap.getFieldAt(y, x + 1).getObstacle().isCrossable())))
            dfs(y, x + 1, searched, distance - 1);

        if (y - 1 >= 0  && distance > 0
                && ((gameMap.getFieldAt(y - 1, x).getObstacle() == null || gameMap.getFieldAt(y - 1, x).getObstacle().isCrossable())))
            dfs(y - 1, x, searched, distance - 1);

    }


    /**
     * Calculate which points are in a given radius from a given point
     *
     * @param y      coordinate
     * @param x      coordinate
     * @param radius in witch to check
     * @return list of point in a circle of a given radius
     */
    public static List<int[]> getPointsInRangePyt(int y, int x, int radius) {
        List<int[]> listOfPoints = new ArrayList<>();
        int yi = y - radius, xi;
        if (yi < 0) yi = 0;
        for (; yi <= y + radius && yi < gameMap.getMaxY(); yi++) {
            xi = x - radius;
            if (xi < 0) xi = 0;
            for (; xi <= x + radius && xi < gameMap.getMaxX(); xi++) {
                if (pythagoreanDistance(y, x, yi, xi) <= (double) radius)
                    listOfPoints.add(new int[]{yi, xi});
            }
        }

        return listOfPoints;
    }


    /**
     * Calculate possible moves in a given time for a hero
     *
     * @param y     coordinate
     * @param x     coordinate
     * @param range to check
     * @return List of two element integer arrays
     */
    public static List<int[]> getPointsInRangeDFS(int y, int x, int range) {
        boolean[][] searched = new boolean[gameMap.getMaxY()][gameMap.getMaxX()]; // false by default
        //int distance = hero.getMoveDistance();
        dfs(y, x, searched, range);
        List<int[]> movesList = new ArrayList<>();
        for (int yi = 0; yi < gameMap.getMaxY(); yi++)//can be optimized
            for (int xi = 0; xi < gameMap.getMaxX(); xi++)
                if (searched[yi][xi])
                    movesList.add(new int[]{yi, xi});
        return movesList;
    }

    /**
     * Checks if a given hero has a line of sight to a given field
     *
     * @param hero to be checked
     * @param yt   coordinate of a given field (target)
     * @param xt   coordinate of a given field (target)
     * @return true if the hero sees the field, false otherwise
     */
    public static boolean isInLineOfSight(Hero hero, int yt, int xt) {
        List<double[]> suspectedColisions = new ArrayList<>();
        int ys, ye, xs, xe;
        if (hero.getMapX() > xt) {
            xs = xt;
            xe = hero.getMapX();
        } else {
            xs = hero.getMapX();
            xe = xt;
        }
        if (hero.getMapY() > yt) {
            ys = yt;
            ye = hero.getMapY();
        } else {
            ys = hero.getMapY();
            ye = yt;
        }
        //Search map for things that may get in a way of line of sight
        for (int y = ys; y <= ye; y++)
            for (int x = xs; x <= xe; x++) {
                if (gameMap.getFieldAt(y, x).getHero() != null ||
                        (gameMap.getFieldAt(y, x).getObstacle() != null && !gameMap.getFieldAt(y, x).getObstacle().isCrossable())) {
                    if (!(x == hero.getMapX() && y == hero.getMapY()) && !(x == xt && y == yt))
                        suspectedColisions.add(new double[]{y, x});
                }
            }
        //Calculating coefficients of a straight line representing the line of sight y=ax+b
        double a;
        if (hero.getMapX() != xt) {//because you can't divide by 0
            if (hero.getMapX() > xt) a = (hero.getMapY() - yt) / (double) (hero.getMapX() - xt);
            else a = (yt - hero.getMapY()) / (double) (xt - hero.getMapX());
            double b = hero.getMapY() - hero.getMapX() * a;
            double y1;
            //Check 10 times per one field if anything is in the way
            //WOWOWOOOOOOOOOOOOOOOOOOOW YOU CAN DO THAT?!?!??!?!?!??!?!?!!
            outer: for (double xi = xs; xi < (double) xe; xi += 0.01) {
                y1 = xi * a + b;
                for (double[] doubles : suspectedColisions) {
                    if (y1 > doubles[0] - 0.5 && y1 < doubles[0] + 0.5 && xi > doubles[1] - 0.5 && xi < doubles[1] + 0.5) {
                        //break outer;
                        return false;
                    }
                }

            }
        } else {
            for (int y = ys + 1; y < ye; y++) {
                if (gameMap.getFieldAt(y, xs).getHero() != null ||
                        (gameMap.getFieldAt(y, xs).getObstacle() != null && !gameMap.getFieldAt(y, xs).getObstacle().isCrossable()))
                    return false;
            }
        }
        return true;
    }

    /**
     * @param hero
     * @return list of possible skill
     */
    public static ArrayList<Skill> getPossibleSkills(Hero hero){
        ArrayList<Skill> possibleSkills = new ArrayList<>();
        for (int i = 0; i < hero.getSkillsList().size(); i++) {
            possibleSkills.add(hero.getSkillsList().get(i));
        }
        return possibleSkills;
    }

    /**
     * Calculate possible target in a given time for a hero
     *
     * @param hero        to calculate for
     * @param skillNumber to calculate for
     * @return List of two element integer arrays
     */
    public static List<int[]> getPossibleTargets(Hero hero, int skillNumber) {
        Skill skill = hero.getSkillsList().get(skillNumber);
        List<int[]> potentialTarget;
        List<int[]> possibleTargets = new ArrayList<>();

        if (skill.getClass().equals(Necromancy.class)) {
            potentialTarget = getPointsInRangePyt(hero.getMapY(), hero.getMapX(), skill.getDistance());
            for (int[] ints : potentialTarget) {
                if (gameMap.getFieldAt(ints[0], ints[1]).getHero() != null &&
                        gameMap.getFieldAt(ints[0], ints[1]).getHero().getOwner().equals(hero.getOwner()))
                    possibleTargets.add(ints);
            }
        } else {
            switch (skill.getUseDistance()) {
                case Lob: {
                    possibleTargets = getPointsInRangePyt(hero.getMapY(), hero.getMapX(), skill.getDistance());
                    break;
                }
                case NoLob: {
                    potentialTarget = getPointsInRangePyt(hero.getMapY(), hero.getMapX(), skill.getDistance());
                    for (int[] ints : potentialTarget) {
                        if (isInLineOfSight(hero, ints[0], ints[1]))
                            possibleTargets.add(ints);
                    }
                    break;
                }
                case Flood: {
                    possibleTargets = getPointsInRangeDFS(hero.getMapY(), hero.getMapX(), skill.getDistance());
                    break;
                }
            }
        }


        //remove unAttackable entity
        for (int i = 0; i < possibleTargets.size(); i++) {
            if (gameMap.getFieldAt(possibleTargets.get(i)[0], possibleTargets.get(i)[1]).getObstacle() != null &&
                    !gameMap.getFieldAt(possibleTargets.get(i)[0], possibleTargets.get(i)[1]).getObstacle().isAttackable()) {
                possibleTargets.remove(i);
                i--;
            }
        }
        return possibleTargets;
    }


    /**
     * @param hero        which use skill
     * @param skillNumber in hero.skillList
     * @param y           target y
     * @param x           target x
     * @return true if move is valid, false otherwise
     */
    public static boolean validator(Hero hero, int skillNumber, int y, int x) {
        List<int[]> possibleTargets = getPossibleTargets(hero, skillNumber);
        for (int[] possibleTarget : possibleTargets) {
            if (possibleTarget[0] == y && possibleTarget[1] == x)
                return true;
        }
        return false;
    }


    /**
     * if hero want move on field where stays, don't do move
     * @param hero which change position
     * @param y    new hero's coordinate
     * @param x    new hero's coordinate
     */
    public static void initChangePosition(Hero hero, int y, int x){
        Field field = gameMap.getFieldAt(y, x);
        Hero otherHero = field.getHero();
        if(!hero.equals(otherHero))
            changePosition(hero,y,x);
    }

    /**
     * Move hero on new coordinate
     *
     * @param hero which change position
     * @param y    new hero's coordinate
     * @param x    new hero's coordinate
     */
    public static void changePosition(Hero hero, int y, int x) {
        Field field = gameMap.getFieldAt(y, x);
        Hero otherHero = field.getHero();

        //if hero moves on unFixed obstacle
        Obstacle obstacle = field.getObstacle();
        if (obstacle != null && !obstacle.isFixed())
            collision(hero, y, x);

            //when new coordinate are clear no hero no trap
        else if (otherHero == null && obstacle == null) {
            gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
            hero.setMapY(y);
            hero.setMapX(x);
            field.addHero(hero);
        }
        else if (otherHero != null && obstacle == null) {
            if (hero.getWeight() > otherHero.getWeight()) {
                collision(otherHero, otherHero.getMapY(), otherHero.getMapX());
                gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                hero.setMapY(y);
                hero.setMapX(x);
                field.addHero(hero);
                collision(otherHero, otherHero.getMapY(), otherHero.getMapX());
            } else
                collision(hero, y, x);
        }

        // when new coordinate include trap and may include hero
        else if (obstacle.getClass().equals(Trap.class)) {
            Trap trap = (Trap) obstacle;
            if (otherHero != null) {
                if (hero.getWeight() > otherHero.getWeight()) {
                    collision(otherHero, otherHero.getMapY(), otherHero.getMapX());
                    gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                    hero.setMapY(y);
                    hero.setMapX(x);
                    field.addHero(hero);
                    changeHPbyObstacle(hero, trap.getDamage());
                    field.addObstacle(null);
                } else
                    collision(hero, y, x);
            } else {
                gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                hero.setMapY(y);
                hero.setMapX(x);
                field.addHero(hero);
                changeHPbyObstacle(hero, trap.getDamage());
                field.addObstacle(null);
            }
        }
    }


    /**
     * Solve collision when two hero on one coordinate
     *
     * @param hero which is pushed
     * @param y    collision coordinate
     * @param x    collision coordinate
     */
    public static void collision(Hero hero, int y, int x) {
        Random random = new Random();
        //move 0-up,1-right,2-down,3-left
        int direction;
        rnd:
        for(;;) {
            direction = random.nextInt(4);
            switch (direction) {
                case 0:
                    if (gameMap.getFieldAt(y + 1, x).getObstacle() == null || gameMap.getFieldAt(y + 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y + 1, x);
                        break rnd;
                    }
                    break;
                case 1:
                    if (gameMap.getFieldAt(y, x + 1).getObstacle() == null || gameMap.getFieldAt(y, x + 1).getObstacle().isCrossable()) {
                        changePosition(hero, y, x + 1);
                        break rnd;
                    }
                    break;
                case 2:
                    if (gameMap.getFieldAt(y - 1, x).getObstacle() == null || gameMap.getFieldAt(y - 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y - 1, x);
                        break rnd;
                    }
                    break;
                case 3:
                    if (gameMap.getFieldAt(y, x - 1).getObstacle() == null || gameMap.getFieldAt(y, x - 1).getObstacle().isCrossable()) {
                        changePosition(hero, y, x - 1);
                        break rnd;
                    }
                    break;
            }
        }
    }


    /**
     * @param hero        which use skill
     * @param skillNumber skill number in skillsList
     * @param y           target coordinate
     * @param x           target coordinate
     * @return true if skill realize, false if don't
     */
    // when cause this method, skills target have to be valid
    public static boolean useSkill(Hero hero, int skillNumber, int y, int x){
        if (!hero.isAlive())
            return false;

        Skill skill = hero.getSkillsList().get(skillNumber);
        //necromancy
        if (skill instanceof Necromancy) {
            if (gameMap.getFieldAt(y, x).getHero() != null && !gameMap.getFieldAt(y, x).getHero().isAlive()) {
                Player owner = hero.getOwner();
                Hero resurrected = gameMap.getFieldAt(y, x).getHero();
                resurrected.setAlive(true);
                resurrected.setHealth((int) (resurrected.getMaxHealth() * 0.5));
                owner.addHero(resurrected);
                resurrected.setOwner(owner);
            }
        } else {
            int value = skill.getValue();
            int range = skill.getRange();
            List<int[]> toUse;
            switch (skill.getRangeType()) {
                case FloodRange: {
                    toUse = getPointsInRangeDFS(y, x, range);
                    for (int[] ints : toUse) {
                        changeHPbyHero(hero, fieldAt(ints[0], ints[1]), value);
                    }
                    break;
                }
                case AreaRange: {
                    toUse = getPointsInRangePyt(y, x, range);
                    for (int[] ints : toUse) {
                        changeHPbyHero(hero, fieldAt(ints[0], ints[1]), value);
                    }
                    break;
                }
                case PointRange: {
                    changeHPbyHero(hero, fieldAt(y, x), value);
                }
                break;
            }
        }
        if (skill.getAfterAttack().equals(SkillProperty.GoToTarget))
            changePosition(hero, y, x);
        if (skill instanceof Fireball){
            int[] coords = GameEngine.mapToGuiConvert(x, y);
            ((Fireball)skill).throwFireball((int)hero.getY(),(int)hero.getX(),coords[1],coords[0]);
        }
        if (skill instanceof Arrow){
            int[] coords = GameEngine.mapToGuiConvert(x, y);
            ((Arrow)skill).fireArrow((int)hero.getY(),(int)hero.getX(),coords[1],coords[0]);
        }


        if (skill.getAfterAttack().equals(SkillProperty.GoToTarget))
            initChangePosition(hero,y,x);
        return true;
    }


    /**
     * @param hero which takeDamage
     * @param field target
     * @param value +/-;
     * set hero to isAlive = false and remove hero from player list
     */
    public static void changeHPbyHero(Hero hero, Field field, int value) {
        if (field.getHero() != null && !field.getHero().getOwner().equals(hero.getOwner())) {
            field.getHero().setHealth(field.getHero().getHealth() + value);
            if (field.getHero().getHealth() <= 0) {
                field.getHero().setAlive(false);
            }
        }
        else if(field.getObstacle()!=null&&field.getObstacle().isAttackable()) {
            //obstacle-hp
        }
    }


    /**
     * @param hero whose HP is changing
     * @param value of changing
     */
    public static void changeHPbyObstacle(Hero hero, int value) {
        hero.setHealth(hero.getHealth()+value);
        if(hero.getHealth()<=0) {
            hero.setAlive(false);
        }
    }

    public static void addObstacle(Obstacle obstacle){
        gameMap.getFieldAt(obstacle.getMapY(),obstacle.getMapX()).addObstacle(obstacle);
    }
    public static void addHero(Hero hero){
        gameMap.getFieldAt(hero.getMapY(),hero.getMapX()).addHero(hero);
    }
    public static Field fieldAt(int y, int x) { return gameMap.getFieldAt(y,x); }
    public static GameMap getGameMap(){
        return gameMap;
    }
    /**
     * Translate Actor's coordinates to map coordinates and
     */
    public static int[] guiToMapConvert(int x,int y){
        return new int[]{(x-10)/StrategicGame.TEXTUREWIDTH,(StrategicGame.HEIGHT-y-StrategicGame.TEXTUREHEIGHT-10)/32};
    }
    public static int[] mapToGuiConvert(int x,int y) {
        return new int[]{x * StrategicGame.TEXTUREWIDTH + 10, StrategicGame.HEIGHT - (y + 1) * StrategicGame.TEXTUREHEIGHT - 10};
    }

    public static double getDegreeBetween(int yh, int xh, int yt, int xt){
        Vector2 rotationVector = new Vector2(xt - xh, yt - yh);
        double beta = rotationVector.angleRad();
        beta *= MathUtils.radiansToDegrees;
        System.out.println(beta);
        return beta-90;

    }

}
