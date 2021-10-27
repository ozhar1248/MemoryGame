package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;

public class MainServer {
    private static final int PORT = 7777;

    public static void main(String[] args) {
        
        try (ServerSocket serversocket = new ServerSocket(PORT)) {
            while (true) {
                new Thread(new PlayerConnection(serversocket.accept())).start();
            }
        }
        catch (IOException e) {
            System.out.println("Server is down");
        }
    }
}
