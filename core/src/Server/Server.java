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
        while (true) {
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


    public static synchronized boolean check() throws IOException {
        if (initPlayer == Server.playerNumber) {
            boolean marker = true;
            for (ServerThread client : activeClients) {
                if (!client.receiver) {
                    marker = false;
                    break;
                }
            }

            if (marker && turns.size() > 0) {
                send(true);
                unlock();
            }
            return marker;
        }
        return false;
    }

    public static synchronized void unlock() {
        for (ServerThread client : activeClients) {
            synchronized (client.lock) {
                client.lock.notify();
                client.receiver = false;
                System.out.println("Unlocking " + client.name);
            }
        }
    }

    public static GameMap getMap() {
        return GameEngine.getGameMap();
    }


    public static synchronized void send(boolean moves) {
        if (moves) {
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
                    System.out.println("to wysyłam: " + postman.getGameMap());
                    System.out.println("Send successful to " + client);
                    client.os.flush();
                } catch (IOException e) {
                    client.dispose();
                    activeClients.remove(client);
                    System.out.println("Send unsuccessful to " + client);
                    i--;
                }
            }
        } else {
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
                    System.out.println("disconnect " + client);
                    activeClients.remove(client);
                    client.dispose();
                }
            }
        }
        turns.clear();
    }


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


    public static synchronized void init() {
        switch (initPlayer) {
            case 4:
                Turn turn = activeClients.get(3).received;
                LogicalHero hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                LogicalHero hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                LogicalHero hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                LogicalHero hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero1.setMapPosition(1, 20);
                hero2.setMapPosition(2, 19);
                hero3.setMapPosition(1, 19);
                hero4.setMapPosition(2, 20);
                GameEngine.getGameMap().getFieldAt(1, 20).addHero(hero1);
                GameEngine.getGameMap().getFieldAt(2, 19).addHero(hero2);
                GameEngine.getGameMap().getFieldAt(1, 19).addHero(hero3);
                GameEngine.getGameMap().getFieldAt(2, 20).addHero(hero4);
            case 3:
                turn = activeClients.get(2).received;
                hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero1.setMapPosition(20, 1);
                hero2.setMapPosition(19, 2);
                hero3.setMapPosition(20, 2);
                hero4.setMapPosition(19, 1);
                GameEngine.getGameMap().getFieldAt(20, 1).addHero(hero1);
                GameEngine.getGameMap().getFieldAt(19, 2).addHero(hero2);
                GameEngine.getGameMap().getFieldAt(20, 2).addHero(hero3);
                GameEngine.getGameMap().getFieldAt(19, 1).addHero(hero4);
            case 2:
                turn = activeClients.get(1).received;
                hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero1.setMapPosition(1, 1);
                hero2.setMapPosition(2, 2);
                hero3.setMapPosition(1, 2);
                hero4.setMapPosition(2, 1);
                GameEngine.getGameMap().getFieldAt(1, 1).addHero(hero1);
                GameEngine.getGameMap().getFieldAt(2, 2).addHero(hero2);
                GameEngine.getGameMap().getFieldAt(1, 2).addHero(hero3);
                GameEngine.getGameMap().getFieldAt(2, 1).addHero(hero4);
            case 1:
                turn = activeClients.get(0).received;
                hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero1.setMapPosition(20, 20);
                hero2.setMapPosition(19, 19);
                hero3.setMapPosition(20, 19);
                hero4.setMapPosition(19, 20);
                GameEngine.getGameMap().getFieldAt(20, 20).addHero(hero1);
                GameEngine.getGameMap().getFieldAt(19, 19).addHero(hero2);
                GameEngine.getGameMap().getFieldAt(20, 19).addHero(hero3);
                GameEngine.getGameMap().getFieldAt(19, 20).addHero(hero4);

        }
        Server.gameInit = true;
    }

    public static boolean look(int ID) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    public static LogicalPlayer get(int ID) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getId() == ID) {
                return player;
            }
        }
        return null;
    }
}
