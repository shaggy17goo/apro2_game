package Server;


import Model.LogicalHeros.LogicalHero;
import Model.LogicalMap.GameMap;
import Model.LogicalPlayer;
import Model.Move;
import Model.Postman;
import Model.Turn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Server {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    public static byte[] password;
    public static ArrayList<ServerThread> activeClients = new ArrayList<>();
    public boolean exit = false;

    public static ArrayList<LogicalPlayer> initialPlayer = new ArrayList<>();
    public static int playerNumber;
    public static int initPlayer = 0;
    static boolean gameInit;

    public static ArrayList<Turn> turns = new ArrayList<>();
    private static final GameEngine gameEngine = new GameEngine(22, 22);

    public Server(int playerNumber, String password) throws IOException, NoSuchAlgorithmException {
        Server.password = md.digest(password.getBytes());
        Server.playerNumber = playerNumber;
        ServerSocket server = new ServerSocket(1701);
        int i = 1;
        System.out.println("Waiting for players...");
        while (!exit) {
            Socket s = server.accept();
            String name = "client" + i;
            i++;
            ServerThread t = new ServerThread(s, name);
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Input number of players: ");
        Scanner input = new Scanner(System.in);
        int numberOfPlayers = input.nextInt();
        System.out.println("Initializing server...");
        new Server(numberOfPlayers, "password");
    }


    /**
     * Check if server threads received date from each of active client
     * If all send data, update game and send moves to each of active client
     *
     * @return false if one or more of active clients didn't send data
     */
    public static synchronized boolean check() {
        if (initPlayer == Server.playerNumber) {
            boolean marker = true;
            for (ServerThread client : activeClients) {
                if (!client.receiver) {
                    marker = false;
                    break;
                }
            }

            if (marker && turns.size() > 0) {
                gameSend();
                unlock();
            }
            return marker;
        }
        return false;
    }

    /**
     * unlock all active clients thread
     */
    public static synchronized void unlock() {
        for (ServerThread client : activeClients) {
            synchronized (client.lock) {
                client.lock.notify();
                client.receiver = false;
                System.out.println("Unlocking " + client.name);
            }
        }
    }

    /**
     * update game state and send postman to each of active client
     */
    public static synchronized void gameSend() {
        ArrayList<Move> sortedMoves = gameEngine.performAction(turns);
        updatePlayersHeroesList();
        Postman postman = new Postman(GameEngine.getGameMap(), sortedMoves, gameEngine.generateNewStack());
        System.out.println(GameEngine.getGameMap());
        ServerThread client;
        for (int i = 0; i < activeClients.size(); i++) {
            client = activeClients.get(i);
            try {
                client.os.reset();
                client.os.writeObject(postman);// sending object
                System.out.println("Send successful to " + client);
                client.os.flush();
            } catch (IOException e) {
                client.dispose();
                activeClients.remove(client);
                System.out.println("Send unsuccessful to " + client);
                System.out.println("Disconnect " + client);

                i--;
            }
        }
    }

    /**
     * send initial package to every client if all connected
     */
    public static synchronized void initSend() {
        gameEngine.generateNewStack();
        for (ServerThread client : activeClients) {
            try {
                client.os.reset();
                client.os.writeObject(GameEngine.getGameMap());// sending object
                client.os.writeObject(GameEngine.getStack());
                System.out.println("Send successful to " + client);
                client.os.flush();

            } catch (IOException e) {
                System.out.println("Send unsuccessful to " + client);
                System.out.println("Disconnect " + client);
                activeClients.remove(client);
                client.dispose();
            }
        }
        turns.clear();
    }


    /**
     * update players heroes list, update references
     */
    public static void updatePlayersHeroesList() {
        for (LogicalPlayer player : Server.initialPlayer) {
            player.getHeroesList().clear();
            for (int i = 0; i < GameEngine.getGameMap().getMaxY(); i++) {
                for (int j = 0; j < GameEngine.getGameMap().getMaxX(); j++) {
                    if (GameEngine.getGameMap().getFieldAt(i, j).getHero() != null &&
                            GameEngine.getGameMap().getFieldAt(i, j).getHero().getOwner().getId() == player.getId())
                        player.addHero(GameEngine.getGameMap().getFieldAt(i, j).getHero());
                }
            }
        }
        System.out.println("playersHeroesList updated");
    }


    /**
     * locate heroes on map at the beginning of game
     */
    public static synchronized void init() {
        switch (initPlayer) {
            case 4:
                initPlayer(3, 1, getMap().getMaxY() - 3);
            case 3:
                initPlayer(2, getMap().getMaxX() - 3, 1);
            case 2:
                initPlayer(1, getMap().getMaxX() - 3, getMap().getMaxY() - 3);
            case 1:
                initPlayer(0, 1, 1);
        }
        Server.gameInit = true;
    }

    /**
     * locate heroes on map at the beginning of game
     */
    private static synchronized void initPlayer(int playerNumber, int CornerX, int CornerY) {
        Turn turn = activeClients.get(playerNumber).received;
        activeClients.get(playerNumber).player = activeClients.get(playerNumber).received.getOwner();
        LogicalHero hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
        LogicalHero hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
        LogicalHero hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
        LogicalHero hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
        hero1.setMapPosition(CornerX, CornerY);
        hero2.setMapPosition(CornerX, CornerY + 1);
        hero3.setMapPosition(CornerX + 1, CornerY);
        hero4.setMapPosition(CornerX + 1, CornerY + 1);
        GameEngine.getGameMap().getFieldAt(CornerX, CornerY).addHero(hero1);
        GameEngine.getGameMap().getFieldAt(CornerX, CornerY + 1).addHero(hero2);
        GameEngine.getGameMap().getFieldAt(CornerX + 1, CornerY).addHero(hero3);
        GameEngine.getGameMap().getFieldAt(CornerX + 1, CornerY + 1).addHero(hero4);
    }


    /**
     * @param ID
     * @return true if initialPlayer list contains player with given ID
     */
    public static boolean lookInitPlayer(int ID) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param ID
     * @return player with given ID
     */
    public static LogicalPlayer getInitPlayer(int ID) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getId() == ID) {
                return player;
            }
        }
        return null;
    }

    /**
     * @param ID
     * @return true if activeClient list contains thread with player with given ID
     */
    public static boolean lookActiveClient(int ID) {
        for (ServerThread thread : activeClients) {
            if (thread.player.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Game map
     */
    public static GameMap getMap() {
        return GameEngine.getGameMap();
    }

}
