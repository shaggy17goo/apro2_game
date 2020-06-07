package Server;


import Model.LogicalPlayer;
import Model.Move;
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

    public ServerThread(Socket sock, String name) throws IOException {
        System.out.println("Player connected, creating thread");
        this.os = new ObjectOutputStream(sock.getOutputStream());
        this.is = new ObjectInputStream(sock.getInputStream());
        this.name = name;
        this.sock = sock;
        this.start();
    }



    @Override
    public void run() {
        System.out.println("Running");
        if (!Server.gameInit) {
            initState();
        } else
            reconnectState();

        Server.updatePlayersHeroesList();
        gameState();
    }


    public void initState(){
        try {
            this.received = (Turn) is.readObject();
            player = received.getOwner();
            receiver = true;
            System.out.println("try connect: " + this);
            if (received.getOwner() != null && Arrays.compare(Server.password, received.getPassHash())==0
                    && !Server.lookInitPlayer(received.getOwner().getId()) && validInitTurn()){
                Server.initPlayer++;
                Server.activeClients.add(this);
                Server.initialPlayer.add(received.getOwner());
            }
            else {
                System.out.println("disconnect " + this);
                this.dispose();
            }
            if (Server.playerNumber == Server.initPlayer) {
                Server.init();
                Server.initSend();
                Server.unlock();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void reconnectState(){
        try {
            this.received = (Turn) is.readObject();
            player = received.getOwner();
            received.clearMoves();
            receiver = true;
            System.out.println("try reconnect " + this);
            if (received.getOwner() != null && Server.lookInitPlayer(received.getOwner().getId())
                    && !Server.lookActiveClient(received.getOwner().getId())
                    && Arrays.compare(Server.password, received.getPassHash())==0) {
                Server.initialPlayer.remove(Server.getInitPlayer(player.getId()));
                Server.initialPlayer.add(player);
                Server.activeClients.add(this);
                os.reset();
                os.writeObject(Server.getMap());// sending object
                os.writeObject(GameEngine.getStack());
                os.flush();
            } else {
                System.out.println("disconnect " + this);
                this.dispose();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void gameState(){
        while (!exit) {
            receiver = false;
            try {
                try {
                    if(player.hasAliveHeroes()) {
                        System.out.println("Waiting for turn from " + this);
                        this.received = (Turn) is.readObject();
                        System.out.println("Receive successful from " + this);
                        Server.turns.add(received);
                    }
                    else {
                        System.out.println("In spectator mode: " + this);
                    }
                    receiver = true;
                } catch (IOException e) {
                    Server.activeClients.remove(this);
                    System.out.println("Receive unsuccessful from " + this);
                    System.out.println("disconnect " + this);
                    synchronized (lock) {
                        System.out.println("lock " + name);
                        Server.check();
                        break;
                    }
                }
                synchronized (lock) {
                    System.out.println("lock " + name);
                    if (!Server.check())
                        lock.wait();
                }
            } catch (InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //to server isn't working too fast
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean validInitTurn(){
        if(received.getMoves().size()!=4)
            return false;
        return true;
    }



    @Override
    public String toString() {
        return name + "(" + player.getNick()+ "): ";
    }


    public void dispose() {
        exit = true;
    }
}
