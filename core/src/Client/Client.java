package Client;

import Model.*;
import Client.GraphicalHeroes.*;
import Client.Map.GameMap;
import com.mygdx.game.StrategicGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public ObjectInputStream is;
    public ObjectOutputStream os;
    public static Turn send;
    public GameMap received;
    private boolean isSend = false;
    private Player player;
    boolean exit = false;
    StrategicGame game;

    public Client(final StrategicGame game, final boolean init) throws Exception {
        Object lock = new Object();
        Socket s = new Socket(game.ip, Integer.parseInt(game.port));
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        this.player = new Player(game.nick);
        this.send = new Turn(player);
        this.game=game;
        send.clearMoves();

        if (init) {
            createTurn(send);
        }

        os.reset();
        os.writeObject(send);
        send.clearMoves();
        isSend = true;
        os.flush();

        final Object finalLock = lock;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (init) {
                    try {
                        received = (GameMap) is.readObject();
                        game.gameEngine.setGameMap(received);
                        System.out.println("Reading...");
                        isSend = false;
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                while (!exit) {
                    synchronized (finalLock) {
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
                                received = (GameMap) is.readObject();
                                System.out.println("Reading...");
                                game.gameEngine.setGameMap(received);
                                isSend = false;
                                send.clearMoves();
                                System.out.println(received);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
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


    private void createTurn(Turn turn) {

        if(game.choseHeroes[0]){
            Archer hero = new Archer(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }
        if(game.choseHeroes[1]){
            Necromancer hero = new Necromancer(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }
        if(game.choseHeroes[2]){
            Paladin hero = new Paladin(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }
        if(game.choseHeroes[3]){
            Priest hero = new Priest(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }
        if(game.choseHeroes[4]){
            Warrior hero = new Warrior(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }
        if(game.choseHeroes[5]){
            Wizard hero = new Wizard(3,4);
            game.player.addHero(hero);
            turn.addMove(new Move(game.player,hero,hero.getSkillsList().get(0),1,1));
            turn.getOwner().addHero(hero);
        }

    }



    public void dispose() {
        exit = true;
    }

}
