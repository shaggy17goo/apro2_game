package Model;

import Model.LogicalMap.GameMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Postman implements Serializable {
    private GameMap gameMap;
    private ArrayList<Move> moves;
    private Stack<Integer> randoms;

    public Postman(GameMap gameMap, ArrayList<Move> moves, Stack<Integer> randoms) {
        this.gameMap = gameMap;
        this.moves = moves;
        this.randoms = randoms;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Stack<Integer> getRandoms() {
        return randoms;
    }
}
