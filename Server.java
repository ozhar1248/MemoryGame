package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 7777;

    public Server() {
        ArrayList<Game> games = new ArrayList<>();
        Game lastGame = new Game();
        try (ServerSocket serversocket = new ServerSocket(PORT)) {
            while (true) {
                Connection player = new Connection(serversocket.accept());
                player.startListening();
                //
                lastGame.addParticipate(player);
                if (lastGame.isFull()) {
                    lastGame.start();
                    games.add(lastGame);
                    lastGame = new Game();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Server is down");
        }
    }

}
