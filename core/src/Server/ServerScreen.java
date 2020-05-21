package Server;
import com.badlogic.gdx.ApplicationAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerScreen extends ApplicationAdapter {
    @Override
    public void create() {
        try {
            new Server(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}