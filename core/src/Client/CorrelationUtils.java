package Client;

import Client.GraphicalHeroes.*;
import Client.Map.Obstacle;
import Client.Map.Trap;
import Client.Map.Wall;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;
import com.mygdx.game.StrategicGame;

public class CorrelationUtils {
    public static Obstacle makeGraphicalObstacleFromLogical(Model.LogicalMap.Obstacle logObstacle) {
        Obstacle obstacle = new Wall(1,1);
        if(logObstacle instanceof Model.LogicalMap.Wall){
            Model.LogicalMap.Wall wall = (Model.LogicalMap.Wall) logObstacle;
            obstacle = new Wall(wall.getMapY(),wall.getMapX());
        }
        else if(logObstacle instanceof Model.LogicalMap.Trap) {
            Model.LogicalMap.Trap trap = (Model.LogicalMap.Trap) logObstacle;
            obstacle = new Trap(trap.getMapY(), trap.getMapX(), trap.getDamage()) {
            };
        }
        return obstacle;
    }

    public static LogicalHero makeLogicalHeroFromGraphical(Hero graphHero, LogicalPlayer logicalPlayer) {
        LogicalHero hero;
        int y = graphHero.getMapY(), x = graphHero.getMapX();
        if (graphHero instanceof Archer) {
            hero = new Model.LogicalHeros.Archer(y,x);
        }
        else if (graphHero instanceof Necromancer) {
            hero = new Model.LogicalHeros.Necromancer(y,x);
        }
        else if (graphHero instanceof Paladin) {
            hero = new Model.LogicalHeros.Paladin(y,x);
        }
        else if (graphHero instanceof Priest) {
            hero = new Model.LogicalHeros.Priest(y,x);
        }
        else if (graphHero instanceof Warrior) {
            hero = new Model.LogicalHeros.Warrior(y,x);
        }
        else{// if (graphHero instanceof Wizard){
            hero = new Model.LogicalHeros.Wizard(y,x);
        }
        hero.setOwner(logicalPlayer);
        GameEngine.logHeroList.add(hero);
        return hero;
    }

    public static Hero makeGraphicalHeroFromLogical(LogicalHero logHero, Player player) {
        Hero hero = new Wizard(1,1);
        if (logHero instanceof Model.LogicalHeros.Archer) {
            hero = new Archer(logHero.getMapY(),logHero.getMapX());
        }
        else if (logHero instanceof Model.LogicalHeros.Necromancer) {
            hero = new Necromancer(logHero.getMapY(),logHero.getMapX());
        }
        else if (logHero instanceof Model.LogicalHeros.Paladin) {
            hero = new Paladin(logHero.getMapY(),logHero.getMapX());
        }
        else if (logHero instanceof Model.LogicalHeros.Priest) {
            hero = new Priest(logHero.getMapY(),logHero.getMapX());
        }
        else if (logHero instanceof Model.LogicalHeros.Warrior) {
            hero = new Warrior(logHero.getMapY(),logHero.getMapX());
        }
        else if (logHero instanceof Model.LogicalHeros.Wizard) {
            hero = new Wizard(logHero.getMapY(),logHero.getMapX());
        }
        hero.setOwner(player);
        hero.setId(logHero.getId());
        GameEngine.graphHeroList.add(hero);
        return hero;
    }

    public static Player makeGraphicalPlayerFromLogical(LogicalPlayer logPlayer) {
        Player player = new Player(logPlayer.getNick());
        player.setID(logPlayer.getId());
        GameEngine.playerList.add(player);
        return player;
    }

    public static LogicalPlayer makeLogicalPlayerFromGraphical(Player player) {
        LogicalPlayer logPlayer = new LogicalPlayer(player.getNick());
        logPlayer.setID(player.getId());
        GameEngine.logicalPlayers.add(logPlayer);
        return logPlayer;
    }

    public static Hero locateGraphHero(LogicalHero logHero){
        for(Hero hero: GameEngine.graphHeroList){
            if(hero.equalToLogical(logHero)) return hero;
        }
        return null;
    }

    public static LogicalHero locateLogHero(Hero graphHero){
        for(LogicalHero logHero: GameEngine.logHeroList){
            if(graphHero.equalToLogical(logHero)) return logHero;
        }
        return null;
    }

    public static float[] translateMapToGUI(int y, int x) {
        // FIXME shouldn't be called directly
        return new float[]{x * 32 + 10, StrategicGame.HEIGHT - (y + 1) * 32 - 10};
    }

    /**
     * Translate Actor's coordinates to map coordinates and
     */
    public static int[] guiToMapConvert(int x, int y) {
        return new int[]{(x - 10) / StrategicGame.TEXTUREWIDTH, (StrategicGame.HEIGHT - y - StrategicGame.TEXTUREHEIGHT - 10) / 32};
    }

    public static int[] mapToGuiConvert(int x, int y) {
        return new int[]{x * StrategicGame.TEXTUREWIDTH + 10, StrategicGame.HEIGHT - (y + 1) * StrategicGame.TEXTUREHEIGHT - 10};
    }
}
