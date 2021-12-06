package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;

public class Server{
    private PlayersCollection players;
    private GamesOrganizer games;

    public Server() {
        players= new PlayersCollection();
        games = new GamesOrganizer(players);

        try (ServerSocket serversocket = new ServerSocket(MemoryGame.PORT)) {
            while (true) {
                Player player = players.waitForNewPlayer(serversocket);
                games.addNewPlayer(player);
            }
        }
        catch (IOException e) {
            System.out.println("Server is down");
        }
    }


}
