package Server;


import Client.Player;
import Model.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ServerThread extends Thread {
    public final Object lock = new Object();
    public ObjectOutputStream os;
    public String name;
    Socket sock;
    public boolean receiver;
    public Turn received;
    public ObjectInputStream is;
    boolean exit;
    public boolean init;
    public Player player;

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name) {
        System.out.println("Creating thread");
        this.is = is;
        this.os = os;
        this.name = name;
        this.sock = sock;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Running");
        if((Server.playerNumber != Server.initPlayer)) {
            try {
                /*os.reset();
                os.writeObject(Server.getMap());// sending object
                os.flush();*/
                this.received = (Turn) is.readObject();
                System.out.println("received object from " + name);
                Server.initPlayer++;
                receiver = true;
                if (received.getOwner() != null) {
                    Server.playersClients.put(this, received.getOwner());
                    Server.players.add(received.getOwner());
                }
                if (Server.playerNumber == Server.initPlayer) {
                    Server.init();
                    Server.send(false);
                    Server.unlock();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else { // reconnect
            try {
                os.reset();
                os.writeObject(Server.getMap());
                os.flush();
                this.received = (Turn) is.readObject();
                Server.turns.add(received);
                System.out.println("received reconnect from " + name);
                if (received.getOwner() != null && Server.look(received.getOwner().getNick())) {
                    Server.playersClients.put(this, Server.get(received.getOwner().getNick()));
                    os.reset();
                    os.writeObject(Server.getMap());// sending object
                    os.flush();
                } else{
                    Server.removeClient(this);
                    Server.playersClients.remove(this);
                    System.out.println("disconnect " + name);
                    this.dispose();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        while (!exit) {
            receiver =false;
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
                Server.removeClient(this);
                Server.playersClients.remove(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }
        }
    }
    public void dispose()
    {
        exit = true;
    }
}
