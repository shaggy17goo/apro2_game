package Model;


import Client.Player;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class representing single turn consisting of 4 moves.
 */
public class Turn implements Serializable {
    private LogicalPlayer owner;
    private Queue<Move> moves;
    private byte[] passHash;
    public Queue<Move> getMoves() {
        return moves;
    }

    public LogicalPlayer getOwner() {
        return owner;
    }

    public Turn(LogicalPlayer owner) {
        this.owner = owner;
        this.moves = new LinkedList<>();
    }
    public void addMove(Move move) {
        moves.add(move);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "owner=" + owner +
                ", moves=" + moves +
                '}';
    }

    public void setPassHash(byte[] passHash) {
        this.passHash = passHash;
    }

    public byte[] getPassHash() {
        return passHash;
    }

    public void clearMoves() {
        this.moves = new LinkedList<>();
    }
}


