package Server;


import Model.LogicalPlayer;
import Model.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;


public class ServerThread extends Thread {
    Socket sock;
    public ObjectOutputStream os;
    public ObjectInputStream is;

    final public Object lock = new Object();
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
        //init game
        if (!Server.gameInit) {
            try {
                this.received = (Turn) is.readObject();
                System.out.println("received object from " + name);
                receiver = true;
                if (received.getOwner() != null && Arrays.compare(Server.password, received.getPassHash())==0) {
                    Server.initPlayer++;
                    Server.activeClients.add(this);
                    Server.initialPlayer.add(received.getOwner());
                }
                if (Server.playerNumber == Server.initPlayer) {
                    Server.init();
                    Server.send(false);
                    Server.unlock();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            // reconnect
        } else {
            try {
                this.received = (Turn) is.readObject();
                received.clearMoves();
                receiver = true;
                System.out.println("received reconnect from " + name);
                if (received.getOwner() != null && Server.look(received.getOwner().getNick())
                        && Arrays.compare(Server.password, received.getPassHash())==0) {
                    Server.activeClients.add(this);
                    os.reset();
                    os.writeObject(Server.getMap());// sending object
                    os.writeObject(GameEngine.getStack());
                    os.flush();
                } else {
                    Server.activeClients.remove(this);
                    System.out.println("disconnect " + name);
                    this.dispose();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        while (!exit) {
            receiver = false;
            try {
                System.out.println("Waiting for turn from " + name);
                this.received = (Turn) is.readObject();
                Server.turns.add(received);
                System.out.println("received object from " + name);
                receiver = true;
                synchronized (lock) {
                    {
                        System.out.println("lock " + name);
                        if (!Server.check())
                            lock.wait();
                    }
                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                Server.activeClients.remove(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }
        }
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        exit = true;
    }
}
