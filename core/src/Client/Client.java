package Client;

import Client.Screens.GameplayScreen;
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
    public Model.LogicalMap.GameMap received;
    private boolean isSend = false;
    private LogicalPlayer player;
    boolean exit = false;
    StrategicGame game;

    public Client(final StrategicGame game, final boolean init) throws Exception {
        Object lock = new Object();
        Socket s = new Socket(game.ip, Integer.parseInt(game.port));
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        this.player = new LogicalPlayer(game.nick);
        this.send = new Turn(player);
        this.game = game;
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
                        received = (Model.LogicalMap.GameMap) is.readObject();
                        GameplayScreen.flag = true;
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
                                received = (Model.LogicalMap.GameMap) is.readObject();
                                System.out.println("Reading...");
                                //GameEngine.setGraphGameMap(received);
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

        if (game.choseHeroes[0]) {
            Model.LogicalHeros.Archer hero = new Model.LogicalHeros.Archer(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }
        if (game.choseHeroes[1]) {
            Model.LogicalHeros.Necromancer hero = new Model.LogicalHeros.Necromancer(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }
        if (game.choseHeroes[2]) {
            Model.LogicalHeros.Paladin hero = new Model.LogicalHeros.Paladin(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }
        if (game.choseHeroes[3]) {
            Model.LogicalHeros.Priest hero = new Model.LogicalHeros.Priest(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }
        if (game.choseHeroes[4]) {
            Model.LogicalHeros.Warrior hero = new Model.LogicalHeros.Warrior(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }
        if (game.choseHeroes[5]) {
            Model.LogicalHeros.Wizard hero = new Model.LogicalHeros.Wizard(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
            turn.getOwner().addHero(hero);
        }

    }


    public void dispose() {
        exit = true;
    }

}
