package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Network {

    private String address;
    private int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private static boolean connection;

    public Network(String address,int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = new Socket(address,port);
        this.connection = true;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void write(Object obj) throws IOException {
        oos.writeObject(obj);
    }
    public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }


    public void closeStream() throws IOException {
        ois.close();
        oos.close();
        socket.close();
        connection = false;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public static boolean isConnection() {
        return connection;
    }
}
