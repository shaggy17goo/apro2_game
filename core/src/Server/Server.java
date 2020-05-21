package Server;


import Client.GraphicalHeroes.Hero;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalMap.GameMap;
import Model.LogicalPlayer;
import Model.Move;
import Model.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;


public class Server {
    public static ArrayList<ServerThread> clients = new ArrayList<>();
    public static HashMap<ServerThread, LogicalPlayer> playersClients = new HashMap<>();
    public static ArrayList<LogicalPlayer> players = new ArrayList<>();
    public static ArrayList<Turn> turns = new ArrayList<>();
    private static GameEngine gameEngine = new GameEngine(22, 22);
    public static int playerNumber;
    public static int initPlayer = 0;
    static boolean gameInit;

    public Server(int playerNumber) throws IOException {
        Server.playerNumber = playerNumber;
        ServerSocket server = new ServerSocket(1701);
        int i = 1;

        while (true) {
            Socket s = server.accept();
            String name = "client " + i;
            i++;
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            ServerThread t = new ServerThread(s, is, os, name);
            clients.add(t);
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(2);
    }



    public static synchronized boolean check() throws IOException {
        if (clients.size() == Server.playerNumber) {
            boolean marker = true;
            for (ServerThread client : clients) {
                if (!client.receiver) {
                    marker = false;
                    break;
                }
            }
            if (marker) {
                unlock();
                if (initPlayer == playerNumber) {
                    send(true);
                }
            }
            return marker;
        }
        return false;
    }

    public static synchronized void unlock() {
        for (ServerThread client : clients) {
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
            ArrayList<Move> sortedMoves = gameEngine.sortMoves(turns);
            for (ServerThread client : clients) {
                System.out.println("Sending");
                client.os.reset();
                client.os.writeObject(sortedMoves);// sending object
                client.os.flush();
                turns.clear();
            }
        }
        else {
            for (ServerThread client : clients) {
                System.out.println("Sending");
                client.os.reset();
                client.os.writeObject(gameEngine.getGameMap());// sending object
                client.os.flush();
            }

        }
    }

    public static synchronized void removeClient(ServerThread client) {
        clients.remove(client);
    }

    public static synchronized void init() {
        switch (initPlayer) {
            case 4:
                Turn turn = clients.get(3).received;
                clients.get(3).player = clients.get(3).received.getOwner();
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
                turn = clients.get(2).received;
                clients.get(2).player = clients.get(2).received.getOwner();
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
                turn = clients.get(1).received;
                clients.get(1).player = clients.get(1).received.getOwner();
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
                turn = clients.get(0).received;
                clients.get(0).player = clients.get(0).received.getOwner();
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
        for (LogicalPlayer player : players) {
            if (player.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public static LogicalPlayer get(String nick) {
        for (LogicalPlayer player : players) {
            if (player.getNick().equals(nick)) {
                return player;
            }
        }
        return null;
    }
}
