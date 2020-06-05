package Client;

import Client.Screens.WaitingScreen;
import Model.LogicalPlayer;
import Model.Move;
import Model.Postman;
import Model.Turn;
import com.mygdx.game.StrategicGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Stack;

/**
 * Class for connection with server
 */
public class Client {
    public ObjectInputStream is;
    public ObjectOutputStream os;
    public static Turn send;
    public Model.LogicalMap.GameMap receivedMap;
    public Postman receivedPostman;
    private boolean isSend;
    private LogicalPlayer player;
    boolean exit = false;
    StrategicGame game;

    public Client(final StrategicGame game, final boolean init) throws Exception {
        Object lock = new Object();
        Socket s = new Socket(game.ip, Integer.parseInt(game.port));
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        this.player = game.logicalPlayer;
        this.send = new Turn(player);
        this.game = game;
        send.clearMoves();

        if (init) {
            createTurn(send);
            send.setPassHash(game.passHash);
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
                        receivedMap = (Model.LogicalMap.GameMap) is.readObject();
                        game.gameEngine.setStack((Stack<Integer>) is.readObject());
                        WaitingScreen.readyToGame = true; //Change waiting screen for GameplayScreen
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
                                receivedPostman = (Postman) is.readObject();
                                System.out.println("dostałem");
                                GameEngine.performTurn(receivedPostman.getMoves());
                                GameEngine.setLogGameMap(receivedPostman.getGameMap());
                                GameEngine.setStack(receivedPostman.getRandoms());
                                GameEngine.updateLogHeroesList();
                                GameEngine.updatePlayersHeroesList();
                                System.out.println("Reading...");
                                if (player.hasAliveHeroes()) {
                                    isSend = false;
                                }
                                send.clearMoves();
                            } catch (IOException | ClassNotFoundException e) {
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


    private void createTurn(Turn turn) {

        if (game.choseHeroes[0]) {
            Model.LogicalHeros.Archer hero = new Model.LogicalHeros.Archer(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }
        if (game.choseHeroes[1]) {
            Model.LogicalHeros.Necromancer hero = new Model.LogicalHeros.Necromancer(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }
        if (game.choseHeroes[2]) {
            Model.LogicalHeros.Paladin hero = new Model.LogicalHeros.Paladin(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }
        if (game.choseHeroes[3]) {
            Model.LogicalHeros.Priest hero = new Model.LogicalHeros.Priest(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }
        if (game.choseHeroes[4]) {
            Model.LogicalHeros.Warrior hero = new Model.LogicalHeros.Warrior(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }
        if (game.choseHeroes[5]) {
            Model.LogicalHeros.Wizard hero = new Model.LogicalHeros.Wizard(3, 4);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 1, 1));
        }

    }


    public void dispose() {
        exit = true;
    }

}
