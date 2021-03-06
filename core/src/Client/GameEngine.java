package Client;

import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.*;
import Client.Map.*;
import Client.Screens.GameplayScreen;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;
import Model.Move;
import Model.Turn;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameEngine {
    private static GameMap graphGameMap;
    private static Model.LogicalMap.GameMap logGameMap;
    private static List<Move> movesQueue = new ArrayList<>();
    private static Stack<Integer> stack = new Stack<>();
    public static List<Hero> graphHeroList = new ArrayList<>();
    public static List<LogicalHero> logHeroList = new ArrayList<>();
    public static List<Player> playerList = new ArrayList<>();
    public static List<LogicalPlayer> logicalPlayers = new ArrayList<>();
    public static boolean isGameEngineReadyToSend = false;
    public static List<DestroyableWall> destroyableWalls = new ArrayList<>();
    public static List<Trap> traps = new ArrayList<>();

    /**
     * Create a new game engine and a map
     */
    public GameEngine() {
        graphGameMap = new GameMap(22, 22);
    }

    /**
     * Create a game engine and construct game map from one received from server
     */
    public GameEngine(Model.LogicalMap.GameMap logGameMap) {
        GameEngine.logGameMap = logGameMap;
        createGraphicalGameMapFromLogical(logGameMap);
    }

    private static void createGraphicalGameMapFromLogical(Model.LogicalMap.GameMap logGameMap) {
        graphGameMap = new GameMap(logGameMap.getMaxY(), logGameMap.getMaxX());
        Player player;
        LogicalPlayer logPlayer;
        Hero hero;
        LogicalHero logHero;
        for (int i = 0; i < logGameMap.getMaxY(); i++) {
            for (int j = 0; j < logGameMap.getMaxX(); j++) {
                if (logGameMap.getFieldAt(i, j).getHero() != null) {
                    logHero = logGameMap.getFieldAt(i, j).getHero();
                    logHeroList.add(logHero);
                    logPlayer = logHero.getOwner();
                    if (!logicalPlayers.contains(logPlayer))
                        logicalPlayers.add(logPlayer);

                    player = CorrelationUtils.makeGraphicalPlayerFromLogical(logPlayer);
                    hero = CorrelationUtils.makeGraphicalHeroFromLogical(logHero, player);
                    graphGameMap.getFieldAt(i, j).addHero(hero);
                }

                if (logGameMap.getFieldAt(i, j).getObstacle() != null) {
                    graphGameMap.getFieldAt(i, j).addObstacle(CorrelationUtils.makeGraphicalObstacleFromLogical(logGameMap.getFieldAt(i, j).getObstacle()));
                }
            }
        }
        updateLogHeroesList();
        updatePlayersHeroesList();
    }

    public static void updateLogHeroesList() {
        LogicalHero logHero;
        logHeroList.clear();
        for (int i = 0; i < logGameMap.getMaxY(); i++) {
            for (int j = 0; j < logGameMap.getMaxX(); j++) {
                if (logGameMap.getFieldAt(i, j).getHero() != null) {
                    logHero = logGameMap.getFieldAt(i, j).getHero();
                    logHeroList.add(logHero);
                }
            }
        }
    }

    public static void updatePlayersHeroesList() {
        for (LogicalPlayer player : logicalPlayers) {
            player.getHeroesList().clear();
            for (LogicalHero hero : logHeroList) {
                if (hero.getOwner().getId() == player.getId()) {
                    player.addHero(hero);
                }
            }
        }
    }


    public static void performTurn(ArrayList<Move> moves) {
        Hero hero;
        for (Move move : moves) {
            hero = CorrelationUtils.locateGraphHero(move.getHero());
            performActions(hero, move.getSkill().getIndex(), move.getMapY(), move.getMapX());
        }
        GameplayScreen.freshUpdate = true;

    }

    public static void performActions(Hero hero, int skillIndex, int targetY, int targetX) {
        if (validator(hero, skillIndex, targetY, targetX)) {
            useSkill(hero, skillIndex, targetY, targetX);
        } else {
            System.out.println("Invalid move");
        }
    }

    public static void sendActionsToServer() {
        System.out.println("Send to server");
        Turn turn = new Turn(movesQueue.get(0).getPlayer());
        for (Move move : movesQueue) {
            turn.addMove(move);
        }
        isGameEngineReadyToSend = true;
        Client.send = turn;
    }

    public static boolean addActionToQueue(Move move) {
        Hero hero = CorrelationUtils.locateGraphHero(move.getHero());
        if (validator(hero, move.getSkill().getIndex(), move.getMapY(), move.getMapX())) {
            System.out.println("Move is valid, added to queue");
            movesQueue.add(move);
            if (movesQueue.size() == StrategicGame.movesPerTour) {
                sendActionsToServer();
                movesQueue.clear();
            }
            return true;
        } else {
            System.out.println("Inputted move is not valid");
            return false;
        }
    }

    public static boolean atLeastOnePlayerWithAliveHeroes() {
        int cnt = 0;
        for (LogicalPlayer player : logicalPlayers) {
            if (player.hasAliveHeroes())
                cnt++;
        }
        return cnt == 1;
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

        if (dfsCondition(y, x - 1, distance))
            dfs(y, x - 1, searched, distance - 1);

        if (dfsCondition(y + 1, x, distance))
            dfs(y + 1, x, searched, distance - 1);

        if (dfsCondition(y, x + 1, distance))
            dfs(y, x + 1, searched, distance - 1);

        if (dfsCondition(y - 1, x, distance))
            dfs(y - 1, x, searched, distance - 1);

    }

    /**
     * Depth first search operating on a stack, modifies a given boolean array "searched"
     *
     * @param y        coordinate to check
     * @param x        coordinate to check
     * @param searched boolean array representing already searched fields
     * @param distance left travel for a hero (derives from Hero.travelDistance)
     */
    private static void stackDfs(int y, int x, boolean[][] searched, int distance) {
        Stack<int[]> stack = new Stack<>();
        stack.add(new int[]{y, x, distance});
        searched[y][x] = true;
        int[] temp;
        while (!stack.empty()) {
            temp = stack.pop();
            y = temp[0];
            x = temp[1];
            distance = temp[2];
            searched[y][x] = true;
            if (dfsCondition(y, x - 1, distance))
                stack.add(new int[]{y, x - 1, distance - 1});

            if (dfsCondition(y + 1, x, distance))
                stack.add(new int[]{y + 1, x, distance - 1});

            if (dfsCondition(y, x + 1, distance))
                stack.add(new int[]{y, x + 1, distance - 1});

            if (dfsCondition(y - 1, x, distance))
                stack.add(new int[]{y - 1, x, distance - 1});
        }
    }

    /**
     * Boolean to make dfs code more clear
     */
    private static boolean dfsCondition(int y, int x, int distance) {
        return x >= 0 && x < graphGameMap.getMaxX() && y >= 0 &&
                y < graphGameMap.getMaxY() && distance > 0 &&
                (graphGameMap.getFieldAt(y, x).getObstacle() == null ||
                        graphGameMap.getFieldAt(y, x).getObstacle().isCrossable());
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
        for (; yi <= y + radius && yi < graphGameMap.getMaxY(); yi++) {
            xi = x - radius;
            if (xi < 0) xi = 0;
            for (; xi <= x + radius && xi < graphGameMap.getMaxX(); xi++) {
                if (MathUtils.pythagoreanDistance(y, x, yi, xi) <= (double) radius)
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
        boolean[][] searched = new boolean[graphGameMap.getMaxY()][graphGameMap.getMaxX()]; // false by default
        stackDfs(y, x, searched, range);
        List<int[]> movesList = new ArrayList<>();
        for (int yi = 0; yi < graphGameMap.getMaxY(); yi++)//can be optimized
            for (int xi = 0; xi < graphGameMap.getMaxX(); xi++)
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
        List<double[]> suspectedCollisions = new ArrayList<>();
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
                if (graphGameMap.getFieldAt(y, x).getHero() != null ||
                        (graphGameMap.getFieldAt(y, x).getObstacle() != null && !graphGameMap.getFieldAt(y, x).getObstacle().isCrossable())) {
                    if (!(x == hero.getMapX() && y == hero.getMapY()) && !(x == xt && y == yt))
                        suspectedCollisions.add(new double[]{y, x});
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
            for (double xi = xs; xi < (double) xe; xi += 0.01) {
                y1 = xi * a + b;
                for (double[] doubles : suspectedCollisions) {
                    if (y1 > doubles[0] - 0.5 && y1 < doubles[0] + 0.5 && xi > doubles[1] - 0.5 && xi < doubles[1] + 0.5) {
                        return false;
                    }
                }

            }
        } else {
            for (int y = ys + 1; y < ye; y++) {
                if (graphGameMap.getFieldAt(y, xs).getHero() != null ||
                        (graphGameMap.getFieldAt(y, xs).getObstacle() != null && !graphGameMap.getFieldAt(y, xs).getObstacle().isCrossable()))
                    return false;
            }
        }
        return true;
    }

    /**
     * @param hero Hero whose skills we get
     * @return list of possible skill
     */
    public static ArrayList<Skill> getPossibleSkills(Hero hero) {
        return new ArrayList<>(hero.getSkillsList());
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
                if (graphGameMap.getFieldAt(ints[0], ints[1]).getHero() != null && !graphGameMap.getFieldAt(ints[0], ints[1]).getHero().isAlive() &&
                        graphGameMap.getFieldAt(ints[0], ints[1]).getHero().getOwner().equals(hero.getOwner()))
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
            if (graphGameMap.getFieldAt(possibleTargets.get(i)[0], possibleTargets.get(i)[1]).getObstacle() != null &&
                    !graphGameMap.getFieldAt(possibleTargets.get(i)[0], possibleTargets.get(i)[1]).getObstacle().isAttackable()) {
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
     *
     * @param hero which change position
     * @param y    new hero's coordinate
     * @param x    new hero's coordinate
     */
    public static void initChangePosition(Hero hero, int y, int x) {
        Field field = graphGameMap.getFieldAt(y, x);
        Hero otherHero = field.getHero();
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
    public static void changePosition(Hero hero, int y, int x) {
        //if hero moves on unFixed obstacle
        Obstacle obstacleOnField = graphGameMap.getFieldAt(y, x).getObstacle();
        Hero heroOnField = graphGameMap.getFieldAt(y, x).getHero();
        if (obstacleOnField != null && !obstacleOnField.isFixed())
            collision(hero, y, x);
            //when new coordinates are clear of obstacles
        else {

            if (heroOnField == null && obstacleOnField == null) {
                graphGameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                hero.setMapY(y);
                hero.setMapX(x);
                graphGameMap.getFieldAt(y, x).addHero(hero);
            }

            //when new coordinates include hero but no obstacle(trap)
            else if (heroOnField != null && obstacleOnField == null) {
                if (hero.getWeight() > heroOnField.getWeight()) {
                    collision(heroOnField, heroOnField.getMapY(), heroOnField.getMapX());
                    graphGameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                    hero.setMapY(y);
                    hero.setMapX(x);
                    graphGameMap.getFieldAt(y, x).addHero(hero);
                } else {
                    collision(hero, y, x);
                }
            }

            // when new coordinates include trap
            else if (obstacleOnField instanceof Trap) {
                Trap trap = (Trap) obstacleOnField;
                //if there is a hero standing on a trap
                if (heroOnField != null) {
                    if (hero.getWeight() > heroOnField.getWeight()) {
                        collision(heroOnField, heroOnField.getMapY(), heroOnField.getMapX());
                        graphGameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                        hero.setMapY(y);
                        hero.setMapX(x);
                        graphGameMap.getFieldAt(y, x).addHero(hero);
                        changeHPbyObstacle(hero, trap.getDamage());
                        graphGameMap.getFieldAt(y, x).addObstacle(null);

                    } else
                        collision(hero, y, x);
                //if there isn't a hero on a trap
                } else {
                    graphGameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(null);
                    hero.setMapY(y);
                    hero.setMapX(x);
                    graphGameMap.getFieldAt(y, x).addHero(hero);
                    changeHPbyObstacle(hero, trap.getDamage());
                    graphGameMap.getFieldAt(y, x).addObstacle(null);
                }
                trap.setWasUsed(true);
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
        //move 0-up,1-right,2-down,3-left
        int direction;
        rnd:
        for (; ; ) {
            direction = stack.pop();
            switch (direction) {
                case 0:
                    if (graphGameMap.getFieldAt(y + 1, x).getObstacle() == null || graphGameMap.getFieldAt(y + 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y + 1, x);
                        break rnd;
                    }
                    break;
                case 1:
                    if (graphGameMap.getFieldAt(y, x + 1).getObstacle() == null || graphGameMap.getFieldAt(y, x + 1).getObstacle().isCrossable()) {
                        changePosition(hero, y, x + 1);
                        break rnd;
                    }
                    break;
                case 2:
                    if (graphGameMap.getFieldAt(y - 1, x).getObstacle() == null || graphGameMap.getFieldAt(y - 1, x).getObstacle().isCrossable()) {
                        changePosition(hero, y - 1, x);
                        break rnd;
                    }
                    break;
                case 3:
                    if (graphGameMap.getFieldAt(y, x - 1).getObstacle() == null || graphGameMap.getFieldAt(y, x - 1).getObstacle().isCrossable()) {
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
    public static boolean useSkill(Hero hero, int skillNumber, int y, int x) {
        if (!hero.isAlive())
            return false;

        Skill skill = hero.getSkillsList().get(skillNumber);
        Animation.animate(hero, skill, y, x);

        //necromancy
        if (skill instanceof Necromancy) {
            if (graphGameMap.getFieldAt(y, x).getHero() != null && !graphGameMap.getFieldAt(y, x).getHero().isAlive()) {
                Player owner = hero.getOwner();
                Hero resurrected = graphGameMap.getFieldAt(y, x).getHero();
                resurrected.setAlive(true);
                resurrected.setHealth((int) (resurrected.getMaxHealth() * 0.5));
                resurrected.setOwner(owner);
            }
        } else if (skill instanceof PlaceWall) {
            ((PlaceWall) skill).addWallToList(y, x);
        } else if (skill instanceof PlaceTrap) {
            ((PlaceTrap) skill).putTrap(y, x, skill.getValue());
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

        if (skill.getAfterAttack().equals(SkillProperty.GoToTarget)) {
            initChangePosition(hero, y, x);
        }
        return true;
    }


    /**
     * @param hero  which takeDamage
     * @param field target
     * @param value +/-;
     *              set hero to isAlive = false and remove hero from player list
     */
    public static void changeHPbyHero(Hero hero, Field field, int value) {
        Hero heroOnField = field.getHero();
        if (heroOnField != null &&
                (!heroOnField.getOwner().equals(hero.getOwner())
                        || (heroOnField.getOwner().equals(hero.getOwner()) && value > 0))) {
            heroOnField.setHealth(heroOnField.getHealth() + value);
            if (heroOnField.getHealth() <= 0) {
                heroOnField.setAlive(false);
            }
        } else {
            Obstacle obstacleOnField = field.getObstacle();
            if (obstacleOnField instanceof DestroyableWall) {
                DestroyableWall wall = (DestroyableWall) obstacleOnField;
                wall.durability += value;
                if (wall.durability <= 0) {
                    GameEngine.getGraphGameMap().getFieldAt(wall.getMapY(), wall.getMapX()).addObstacle(null);
                }
            }
        }
    }


    /**
     * @param hero  whose HP is changing
     * @param value of changing
     */
    public static void changeHPbyObstacle(Hero hero, int value) {
        hero.setHealth(hero.getHealth() + value);
        if (hero.getHealth() <= 0) {
            hero.setAlive(false);
        }
    }

    public static void addObstacle(Obstacle obstacle) {
        graphGameMap.getFieldAt(obstacle.getMapY(), obstacle.getMapX()).addObstacle(obstacle);
    }

    public static void addHero(Hero hero) {
        graphGameMap.getFieldAt(hero.getMapY(), hero.getMapX()).addHero(hero);
    }

    public static Field fieldAt(int y, int x) {
        return graphGameMap.getFieldAt(y, x);
    }

    public static GameMap getGraphGameMap() {
        return graphGameMap;
    }

    public static void setGraphGameMap(GameMap graphGameMap) {
        GameEngine.graphGameMap = graphGameMap;
    }


    public static void setLogGameMap(Model.LogicalMap.GameMap logGameMap) {
        GameEngine.logGameMap = logGameMap;
    }

    public static Model.LogicalMap.GameMap getLogGameMap() {
        return logGameMap;
    }


    public static void setStack(Stack<Integer> stack) {
        GameEngine.stack = stack;
    }

    @Override
    public String toString() {
        return graphGameMap.toString();
    }
}
