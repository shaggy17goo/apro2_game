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
    final Object lock = new Object();
    StrategicGame game;

    /**
     * create client thread and try connect with server
     *
     * @throws Exception if server doesn't response
     */
    public Client(final StrategicGame game, final boolean init) throws Exception {
        Socket s = new Socket(game.ip, Integer.parseInt(game.port));
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        this.player = game.logicalPlayer;
        this.send = new Turn(player);
        this.game = game;

        Thread t = new Thread(() -> {
            try {
                if (init)
                    initState();
                gameState();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Disconnect");
            }
        });
        t.start();
    }

    /**
     * Create and sand init Turn
     * Waiting for first game map from server
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void initState() throws IOException, ClassNotFoundException {
        send.clearMoves();
        createInitTurn(send);
        send.setPassHash(game.passHash);
        os.reset();
        os.writeObject(send);

        send.clearMoves();
        isSend = true;
        os.flush();
        receivedMap = (Model.LogicalMap.GameMap) is.readObject();
        game.gameEngine.setStack((Stack<Integer>) is.readObject());
        WaitingScreen.readyToGame = true;
        System.out.println("Reading...");
        isSend = false;

    }

    /**
     * sending turn to server
     * waiting for moves, map, and stack from server
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void gameState() throws IOException, ClassNotFoundException {
        while (!exit) {
            synchronized (lock) {
                if (send != null && !isSend && GameEngine.isGameEngineReadyToSend) {
                    GameEngine.isGameEngineReadyToSend = false;

                    System.out.println("Sending...");
                    os.reset();
                    os.writeObject(send);
                    isSend = true;
                    os.flush();
                    send.clearMoves();

                }

                if (isSend) {
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
                }
            }
        }
    }


    /**
     * create initial turn - info about chosen heroes
     *
     * @param turn
     */
    private void createInitTurn(Turn turn) {
        if (game.choseHeroes[0]) {
            Model.LogicalHeros.Archer hero = new Model.LogicalHeros.Archer(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[1]) {
            Model.LogicalHeros.Necromancer hero = new Model.LogicalHeros.Necromancer(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[2]) {
            Model.LogicalHeros.Paladin hero = new Model.LogicalHeros.Paladin(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[3]) {
            Model.LogicalHeros.Priest hero = new Model.LogicalHeros.Priest(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[4]) {
            Model.LogicalHeros.Warrior hero = new Model.LogicalHeros.Warrior(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[5]) {
            Model.LogicalHeros.Wizard hero = new Model.LogicalHeros.Wizard(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[6]) {
            Model.LogicalHeros.Angel hero = new Model.LogicalHeros.Angel(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
        if (game.choseHeroes[7]) {
            Model.LogicalHeros.CYCLOPE hero = new Model.LogicalHeros.CYCLOPE(0, 0);
            game.logicalPlayer.addHero(hero);
            hero.generateID();
            turn.addMove(new Move(game.logicalPlayer, hero, hero.getSkillsList().get(0), 0, 0));
        }
    }
}
