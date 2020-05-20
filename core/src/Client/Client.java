package Client;

import Model.*;
import Model.GraphicalHeroes.Archer;
import Model.GraphicalHeroes.Paladin;
import Model.GraphicalHeroes.Priest;
import Model.GraphicalHeroes.Warrior;
import Model.GraphicalSkills.Arrow;
import Model.Map.GameMap;
import com.mygdx.game.StrategicGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public ObjectInputStream is;
    public ObjectOutputStream os;
    public static Turn send;
    private ArrayList<Move> received;
    private boolean isSend=false;
    boolean exit=false;
    private final Player player;

    public Client(final boolean init) throws Exception {
        //Todo reconnecting marked as not init;
        Socket s = new Socket("127.0.0.1", 1701);
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Object lock = new Object();
        player = new Player("mksochota16");
        StrategicGame.player = player;
        send = new Turn(player);
        //received = (ArrayList<Move>) is.readObject();
        System.out.println("Reading...");
        if(init) {
            createTurn(send);
        }

        System.out.println("Sending...");
        os.reset();
        os.writeObject(send);
        send.clearMoves();
        isSend = true;
        os.flush();
        final Object finalLock = lock;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(init) {
                    //received = (ArrayList<Move>) is.readObject();
                    System.out.println("Reading...");
                    isSend = false;
                }
                try {
                    received = (ArrayList<Move>) is.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (!exit) {
                    synchronized (finalLock){
                        if (send != null && !isSend && GameEngine.isGameEngineReadyToSend) {
                            GameEngine.isGameEngineReadyToSend = false;
                            try {
                                System.out.println("Sending...");
                                os.reset();
                                os.writeObject(send);
                                isSend = true;
                                os.flush();
                                send.clearMoves();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (isSend) {
                            try {
                                received = (ArrayList<Move>) is.readObject();
                                System.out.println("Reading...");
                                isSend = false;
                                send.clearMoves();
                                for (Move move : received) {
                                    GameEngine.performActions(move);
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println(received);
                        }
                    }
                }

            }
        }
        );
        t.start();
    }

    public Turn getSend() {
        return send;
    }

   /* public GameMap getReceived() {
        return this.received;
    }*/


private void createTurn(Turn turn){
        turn.addMove(new Move(player,new Archer(1,1),new Arrow(0),1,1));
        turn.addMove(new Move(player,new Paladin(2,2),new Arrow(0),1,1));
        turn.addMove(new Move(player,new Warrior(1,2),new Arrow(0),1,1));
        turn.addMove(new Move(player,new Priest(2,1),new Arrow(0),1,1));

        /*if(game.chosen[0]){
            Archer hero = new Archer(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(1,1),new Field(1,1),new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if(game.chosen[1]){
            Necromancer hero = new Necromancer(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(2,2),new Field(2,2),new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if(game.chosen[2]){
            Paladin hero = new Paladin(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(3,3),new Field(3,3),new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if(game.chosen[3]){
            Priest hero = new Priest(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(4,4),new Field(4,4),new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if(game.chosen[4]){
            Warrior hero =new Warrior(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(5,5),new Field(5,5),new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if(game.chosen[5]){
            Wizard hero = new Wizard(turn.getOwner(),3,4);
            turn.addMove((new Move(hero,new Field(6,6),new Field(6,6),new Walk(5))));
            turn.getOwner().addHero(hero);
        }*/

    }


    public void dispose()
    {
        exit = true;
    }

}
