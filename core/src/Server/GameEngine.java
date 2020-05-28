package Server;


import Client.Player;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalMap.Field;
import Model.LogicalMap.GameMap;
import Model.LogicalMap.Obstacle;
import Model.LogicalMap.Trap;
import Model.LogicalPlayer;
import Model.LogicalSkills.LogicalSkill;
import Model.LogicalSkills.Necromancy;
import Model.LogicalSkills.SkillProperty;
import Model.Move;
import Model.Turn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class GameEngine {
    private static Model.LogicalMap.GameMap gameMap;
    private final int movesPerTour = 4;
    private static Random random = new Random();
    private static Stack<Integer> stack= new Stack<>();


    public GameEngine(int maxY, int maxX) {
        gameMap = new GameMap(maxY, maxX);
        try {
            ObjectInputStream read = new ObjectInputStream(new FileInputStream("core/src/Server/map1"));
            gameMap =(GameMap) read.readObject();
            read.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Move> performAction(ArrayList<Turn> toPerform) {
        ArrayList<Move> sortedMoves = sortMoves(toPerform);
        for (int i = 0; i < sortedMoves.size(); i++) {
            if (validator(sortedMoves.get(i).getHero(), sortedMoves.get(i).getSkill().getIndex(),
                    sortedMoves.get(i).getMapY(), sortedMoves.get(i).getMapX())) {
                useSkill(sortedMoves.get(i).getHero(), sortedMoves.get(i).getSkill().getIndex(),
                        sortedMoves.get(i).getMapY(), sortedMoves.get(i).getMapX());
            }
            else {
                System.out.println("Move not valid");
                sortedMoves.remove(sortedMoves.get(i));
                i--;
            }
        }
        updatePlayersHeroesList();
        return sortedMoves;
    }

    public ArrayList<Move> sortMoves(ArrayList<Turn> toSort) {
        PriorityQueue<Move> movesPriorityQueue = new PriorityQueue<>();
        ArrayList<Move> sortedMoves = new ArrayList<>();
        for (int i = 0; i < movesPerTour; i++) {  //cnt move in turn
            for (Turn turn : toSort) {   //first move from each turn
                if (turn.getMoves().size() != 0) {
                    movesPriorityQueue.add(turn.getMoves().poll());
                }
            }
            while (!movesPriorityQueue.isEmpty()) {
                System.out.println(movesPriorityQueue.peek());
                sortedMoves.add(movesPriorityQueue.poll());
            }
        }
        return sortedMoves;
    }


    public void updatePlayersHeroesList(){
        for (LogicalPlayer player: Server.initialPlayer) {
            player.getHeroesList().clear();
            for (int i = 0; i < Server.gameEngine.gameMap.getMaxY(); i++) {
                for (int j = 0; j < Server.gameEngine.gameMap.getMaxX(); j++) {
                    if(Server.gameEngine.getGameMap().getFieldAt(i,j).getHero()!=null&&
                            Server.gameEngine.getGameMap().getFieldAt(i,j).getHero().getOwner().getId()==player.getId())
                        player.addHero(Server.gameEngine.getGameMap().getFieldAt(i,j).getHero());
                }
            }
        }
    }

    @Override
    public String toString() {
        return gameMap.toString();
    }

    /**
     * Calculate distance between two points
     *
     * @return Distance from (x1,y1) to (x2,y2) calculated using pythagorean theorem
     */
    public double pythagoreanDistance(int y1, int x1, int y2, int x2) {
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
    private void dfs(int y, int x, boolean[][] searched, int distance) {
        searched[y][x] = true;

        if (dfsCondition(y, x - 1, distance))
            dfs(y, x - 1, searched, distance - 1);

        if (dfsCondition(y + 1, x, distance))
            dfs(y + 1, x, searched, distance - 1);

        if (dfsCondition(y, x + 1, distance))
            dfs(y, x + 1, searched, distance - 1);

        if (dfsCondition(y - 1, x, distance))
            dfs(y - 1, x, searched, distance - 1);

    }

    private boolean dfsCondition(int y, int x, int distance) {
        return x >= 0 && x < gameMap.getMaxX() && y >= 0 &&
                y < gameMap.getMaxY() && distance > 0 &&
                (gameMap.getFieldAt(y, x).getObstacle() == null ||
                        gameMap.getFieldAt(y, x).getObstacle().isCrossable());
    }

    /**
     * Calculate which points are in a given radius from a given point
     *
     * @param y      coordinate
     * @param x      coordinate
     * @param radius in witch to check
     * @return list of point in a circle of a given radius
     */
    public List<int[]> getPointsInRangePyt(int y, int x, int radius) {
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
    public List<int[]> getPointsInRangeDFS(int y, int x, int range) {
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
    public boolean isInLineOfSight(LogicalHero hero, int yt, int xt) {
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
            outer:
            for (double xi = xs; xi < (double) xe; xi += 0.01) {
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
    public ArrayList<LogicalSkill> getPossibleSkills(LogicalHero hero) {
        ArrayList<LogicalSkill> possibleSkills = new ArrayList<>();
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
    public List<int[]> getPossibleTargets(LogicalHero hero, int skillNumber) {
        LogicalSkill skill = hero.getSkillsList().get(skillNumber);
        List<int[]> potentialTarget;
        List<int[]> possibleTargets = new ArrayList<>();

        if (skill.getClass().equals(Necromancy.class)) {
            potentialTarget = getPointsInRangePyt(hero.getMapY(), hero.getMapX(), skill.getDistance());
            for (int[] ints : potentialTarget) {
                if (gameMap.getFieldAt(ints[0], ints[1]).getHero() != null && !gameMap.getFieldAt(ints[0], ints[1]).getHero().isAlive() &&
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
    public boolean validator(LogicalHero hero, int skillNumber, int y, int x) {
        if(!fieldAt(hero.getMapY(),hero.getMapX()).getHero().equals(hero))
            return false;
        List<int[]> possibleTargets = getPossibleTargets(hero, skillNumber);
        for (int[] possibleTarget : possibleTargets) {
            if (possibleTarget[0] == y && possibleTarget[1] == x)
                return true;
        }
        return false;
    }


    /**
     * if hero want move on field where stays, don't do move
     *
     * @param hero which change position
     * @param y    new hero's coordinate
     * @param x    new hero's coordinate
     */
    public void initChangePosition(LogicalHero hero, int y, int x) {
        Field field = gameMap.getFieldAt(y, x);
        LogicalHero otherHero = field.getHero();
        if (!hero.equals(otherHero))
            changePosition(hero, y, x);
    }


    /**
     * Move hero on new coordinate
     *
     * @param hero which change position
     * @param y    new hero's coordinate
     * @param x    new hero's coordinate
     */
    public void changePosition(LogicalHero hero, int y, int x) {
        //if hero moves on unFixed obstacle
        if (gameMap.getFieldAt(y, x).getObstacle() != null && !gameMap.getFieldAt(y, x).getObstacle().isFixed())
            collision(hero, y, x);

            //when new coordinate are clear no hero no trap
        else if (gameMap.getFieldAt(y, x).getHero() == null && gameMap.getFieldAt(y, x).getObstacle() == null) {
            gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
            hero.setY(y);
            hero.setX(x);
            gameMap.getFieldAt(y, x).addHero(hero);
        }

        //when new coordinate include hero but no obstacle(trap)
        else if (gameMap.getFieldAt(y, x).getHero() != null && gameMap.getFieldAt(y, x).getObstacle() == null) {
            if (hero.getWeight() > gameMap.getFieldAt(y, x).getHero().getWeight()) {
                collision(gameMap.getFieldAt(y, x).getHero(), gameMap.getFieldAt(y, x).getHero().getMapY(), gameMap.getFieldAt(y, x).getHero().getMapX());
                gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                hero.setY(y);
                hero.setX(x);
                gameMap.getFieldAt(y, x).addHero(hero);
            } else
                collision(hero, y, x);
        }

        // when new coordinate include trap and may include hero
        else if (gameMap.getFieldAt(y, x).getObstacle() != null && gameMap.getFieldAt(y, x).getObstacle().getClass().equals(Trap.class)) {
            Trap trap = (Trap) gameMap.getFieldAt(y, x).getObstacle();
            if (gameMap.getFieldAt(y, x).getHero() != null) {
                if (hero.getWeight() > gameMap.getFieldAt(y, x).getHero().getWeight()) {
                    collision(gameMap.getFieldAt(y, x).getHero(), gameMap.getFieldAt(y, x).getHero().getMapY(), gameMap.getFieldAt(y, x).getHero().getMapX());
                    gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                    hero.setY(y);
                    hero.setX(x);
                    gameMap.getFieldAt(y, x).addHero(hero);
                    changeHPbyObstacle(hero, trap.getDamage());
                    gameMap.getFieldAt(y, x).addObstacle(null);
                } else
                    collision(hero, y, x);
            } else {
                gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                hero.setY(y);
                hero.setX(x);
                gameMap.getFieldAt(y, x).addHero(hero);
                changeHPbyObstacle(hero, trap.getDamage());
                gameMap.getFieldAt(y, x).addObstacle(null);
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
    public void collision(LogicalHero hero, int y, int x) {

        //move 0-up,1-right,2-down,3-left
        int direction;
        rnd:
        while (true) {
            direction = stack.pop();
            switch (direction) {
                case 0:
                    if (gameMap.getFieldAt(y + 1, x).getObstacle() == null || gameMap.getFieldAt(y + 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y + 1, x);
                        break rnd;
                    }
                case 1:
                    if (gameMap.getFieldAt(y, x + 1).getObstacle() == null || gameMap.getFieldAt(y, x + 1).getObstacle().isCrossable()) {
                        changePosition(hero, y, x + 1);
                        break rnd;
                    }
                case 2:
                    if (gameMap.getFieldAt(y - 1, x).getObstacle() == null || gameMap.getFieldAt(y - 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y - 1, x);
                        break rnd;
                    }
                case 3:
                    if (gameMap.getFieldAt(y, x - 1).getObstacle() == null || gameMap.getFieldAt(y, x - 1).getObstacle().isCrossable()) {
                        changePosition(hero, y, x - 1);
                        break rnd;
                    }
            }
        }
    }

    public Stack<Integer> generateNewStack(){
        stack.clear();
        for (int i = 0; i < 100 ; i++) {
            stack.push(random.nextInt(4));
        }
        return stack;
    }


    /**
     * @param hero        which use skill
     * @param skillNumber skill number in skillsList
     * @param y           target coordinate
     * @param x           target coordinate
     * @return true if skill realize, false if don't
     */
    // when cause this method, skills target have to be valid
    public boolean useSkill(LogicalHero hero, int skillNumber, int y, int x) {
        if (!hero.isAlive())
            return false;

        LogicalSkill skill = hero.getSkillsList().get(skillNumber);
        //necromancy
        if (skill.getClass().equals(Necromancy.class)) {
            if (gameMap.getFieldAt(y, x).getHero() != null && !gameMap.getFieldAt(y, x).getHero().isAlive()) {
                LogicalPlayer owner = hero.getOwner();
                LogicalHero resurrected = gameMap.getFieldAt(y, x).getHero();
                resurrected.setAlive(true);
                resurrected.setHealth((int) (resurrected.getMaxHealth() * 0.5));
                resurrected.setOwner(owner);
            }
            if (skill.getAfterAttack().equals(SkillProperty.GoToTarget))
                initChangePosition(hero, y, x);
        } else {
            int value = skill.getValue();
            int range = skill.getRange();
            List<int[]> toUse;
            switch (skill.getRangeType()) {
                case FloodRange: {
                    toUse = getPointsInRangeDFS(y, x, range);
                    for (int i = 0; i < toUse.size(); i++) {
                        changeHPbyHero(hero, fieldAt(toUse.get(i)[0], toUse.get(i)[1]), value);
                    }
                    break;
                }
                case AreaRange: {
                    toUse = getPointsInRangePyt(y, x, range);
                    for (int i = 0; i < toUse.size(); i++) {
                        changeHPbyHero(hero, fieldAt(toUse.get(i)[0], toUse.get(i)[1]), value);
                    }
                    break;
                }
                case PointRange: {
                    changeHPbyHero(hero, fieldAt(y, x), value);
                }
                break;
            }
            if (skill.getAfterAttack().equals(SkillProperty.GoToTarget)) {
                initChangePosition(hero, y, x);
            }
        }
        return true;
    }


    /**
     * @param hero which takeDamage
     * @param field target
     * @param value +/-;
     * set hero to isAlive = false and remove hero from player list
     */

    public void changeHPbyHero(LogicalHero hero, Field field, int value) {
        if (field.getHero() != null && !field.getHero().getOwner().equals(hero.getOwner())) {
            field.getHero().setHealth(field.getHero().getHealth() + value);
            if (field.getHero().getHealth() <= 0) {
                field.getHero().setAlive(false);
            }
        } else if (field.getObstacle() != null && field.getObstacle().isAttackable()) {
            //obstacle-hp
        }
    }


    /**
     * @param hero  whose HP is changing
     * @param value of changing
     */
    public void changeHPbyObstacle(LogicalHero hero, int value) {
        hero.setHealth(hero.getHealth() + value);
        if (hero.getHealth() <= 0) {
            hero.setAlive(false);
        }
    }

    public void addObstacle(Obstacle obstacle) {
        gameMap.getFieldAt(obstacle.getMapY(), obstacle.getMapX()).addObstacle(obstacle);
    }

    public void addHero(LogicalHero hero) {
        gameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(hero);
    }

    public Field fieldAt(int y, int x) {
        return gameMap.getFieldAt(y, x);
    }

    public static GameMap getGameMap() {
        return gameMap;
    }

    public static Stack<Integer> getStack() {
        return stack;
    }

}
