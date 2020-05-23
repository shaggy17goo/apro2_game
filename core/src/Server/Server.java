package Server;


import Model.LogicalHeros.LogicalHero;
import Model.LogicalMap.GameMap;
import Model.LogicalPlayer;
import Model.Move;
import Model.Postman;
import Model.Turn;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;


public class Server {
    MessageDigest md = MessageDigest.getInstance("MD5");
    private static byte[] password;
    public static ArrayList<ServerThread> activeClients = new ArrayList<>();
    public static HashMap<ServerThread, LogicalPlayer> activePlayersClients = new HashMap<>();

    public static ArrayList<LogicalPlayer> initialPlayer = new ArrayList<>();
    public static int playerNumber;
    public static int initPlayer = 0;
    static boolean gameInit;

    public static ArrayList<Turn> turns = new ArrayList<>();
    private static GameEngine gameEngine = new GameEngine(22, 22);
    
    public Server(int playerNumber, String password) throws IOException, NoSuchAlgorithmException {
        this.password=md.digest(password.getBytes());
        Server.playerNumber = playerNumber;
        ServerSocket server = new ServerSocket(1701);
        int i = 1;
        System.out.println("Waiting for players...");
        while (true) {
            Socket s = server.accept();
            String name = "client " + i;
            i++;
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            ServerThread t = new ServerThread(s, is, os, name);
            activeClients.add(t);
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Input number of players: ");
        Scanner input = new Scanner(System.in);
        int numberOfPlayers = input.nextInt();
        System.out.println("Initializing server...");;
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
            
            if (marker) {
                unlock();
                send(true);
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


    public static synchronized void send(boolean moves) throws IOException {
        if(moves) {
            ArrayList<Move> sortedMoves = gameEngine.performAction(turns);
            Postman postman = new Postman(gameEngine.getGameMap(), sortedMoves, gameEngine.generateNewStack());
            System.out.println(gameEngine.getGameMap());
            for (ServerThread client : activeClients) {
                System.out.println("Sending");
                client.os.reset();
                client.os.writeObject(postman);// sending object
                client.os.flush();
                turns.clear();
            }
        }
        else {
            for (ServerThread client : activeClients) {
                System.out.println("Sending");
                client.os.reset();
                client.os.writeObject(gameEngine.getGameMap());// sending object
                client.os.writeObject(gameEngine.generateNewStack());
                client.os.flush();
            }

        }
    }

    public static synchronized void removeClient(ServerThread client) {
        activeClients.remove(client);
    }

    public static synchronized void init() {
        switch (initPlayer) {
            case 4:
                Turn turn = activeClients.get(3).received;
                activeClients.get(3).player = activeClients.get(3).received.getOwner();
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
                activeClients.get(2).player = activeClients.get(2).received.getOwner();
                hero1 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero2 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero3 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero4 = Objects.requireNonNull(turn.getMoves().poll()).getHero();
                hero1.setMapPosition(20,1);
                hero2.setMapPosition(19,2);
                hero3.setMapPosition(20,2);
                hero4.setMapPosition(19,1);
                GameEngine.getGameMap().getFieldAt(20, 1).addHero(hero1);
                GameEngine.getGameMap().getFieldAt(19, 2).addHero(hero2);
                GameEngine.getGameMap().getFieldAt(20, 2).addHero(hero3);
                GameEngine.getGameMap().getFieldAt(19, 1).addHero(hero4);
            case 2:
                turn = activeClients.get(1).received;
                activeClients.get(1).player = activeClients.get(1).received.getOwner();
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
                activeClients.get(0).player = activeClients.get(0).received.getOwner();
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
    //Hellloooo
    public static boolean look(String nick) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public static LogicalPlayer get(String nick) {
        for (LogicalPlayer player : initialPlayer) {
            if (player.getNick().equals(nick)) {
                return player;
            }
        }
        return null;
    }
}
