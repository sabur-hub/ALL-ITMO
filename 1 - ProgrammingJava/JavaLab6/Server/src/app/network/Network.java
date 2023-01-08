package app.network;

import app.Start;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Network {
    private static boolean connection;
    private Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static ArrayList<String> messageToServerList;

    public Network() {
        try (ServerSocket serverSocket = new ServerSocket(Start.getPORT())) {
            System.out.println("Сервер ожидает соединения по порту №" + serverSocket.getLocalPort());
            socket = serverSocket.accept();
            System.out.println("Соединение установлено");
            connection = true;
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            messageToServerList = new ArrayList<>();
        } catch (IOException  e) {
            e.printStackTrace(System.out);
        }

    }

    public static void write(Object object) {
        messageToServerList.add((String)object);
    }

    public static Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void sendResponseToСlient() throws IOException {
        oos.writeObject(messageToServerList);
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void closeStream() throws IOException {
        ois.close();
        oos.close();
        socket.close();
        connection = false;
    }


}
