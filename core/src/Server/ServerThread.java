package Server;


import Model.LogicalPlayer;
import Model.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;


public class ServerThread extends Thread {
    Socket sock;
    public ObjectOutputStream os;
    public ObjectInputStream is;

    final public Object lock = new Object();
    final public Object lockToUpdate = new Object();

    public LogicalPlayer player;
    public String name;

    public Turn received;
    public boolean receiver;
    boolean exit;

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name) {
        System.out.println("Player connected, creating thread");
        this.is = is;
        this.os = os;
        this.name = name;
        this.sock = sock;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Running");
        if (!Server.gameInit)
            initGame();
        else
            reconnect();

        gameState();
    }


    public void initGame() {
        try {
            this.received = (Turn) is.readObject();
            player = received.getOwner();
            System.out.println(this + " try connect");
            if (received.getOwner() != null && Arrays.compare(Server.password, received.getPassHash()) == 0) {
                System.out.println(this + " connect successful");
                receiver = true;
                Server.initPlayer++;
                Server.activeClients.add(this);
                Server.initialPlayer.add(received.getOwner());
            } else {
                System.out.println(name + " connect unsuccessful");
                this.dispose();
            }
            if (Server.playerNumber == Server.initPlayer) {
                Server.init();
                Server.send(false);
                Server.unlock();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void reconnect() {
        try {
            this.received = (Turn) is.readObject();
            player = received.getOwner();
            received.clearMoves();
            receiver = true;
            System.out.println(this + " try reconnect");
            if (received.getOwner() != null && Server.look(received.getOwner().getNick())
                    && Arrays.compare(Server.password, received.getPassHash()) == 0) {
                System.out.println(this + " reconnect successful");
                Server.activeClients.add(this);
                os.reset();
                os.writeObject(Server.getMap());// sending object
                os.writeObject(GameEngine.getStack());
                os.flush();
            } else {
                Server.activeClients.remove(this);
                System.out.println(this + " reconnect unsuccessful");
                this.dispose();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void gameState() {
        while (!exit) {
            receiver = false;
            synchronized (lockToUpdate) {
                try {
                    if (player.hasAliveHeroes()) {
                        System.out.println("Waiting for turn from " + this);
                        this.received = (Turn) is.readObject();
                        //begin update player
                        player = received.getOwner();
                        Server.initialPlayer.remove(Server.get(player.getId()));
                        Server.initialPlayer.add(player);
                        //end update player
                        Server.turns.add(received);
                        receiver = true;
                        System.out.println("received object from " + this);
                    } else {
                        System.out.println(this + " in spectator mode");
                        receiver = true;
                    }
                    synchronized (lock) {
                        System.out.println("lock " + this);
                        if (!Server.check()) {
                            lock.wait();
                            lockToUpdate.wait();
                        }
                    }

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    Server.activeClients.remove(this);
                    System.out.println("disconnect " + this);
                    this.dispose();
                }
                try {
                    sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public String toString() {
        return name + "(" + player.getNick()+ "): ";
    }

    public void dispose() {
        exit = true;
    }
}
