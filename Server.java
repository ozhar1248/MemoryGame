package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements IObserver{
    private static final int PORT = 7777;
    private PlayersCollection players;
    private Game lastGame;
    private ArrayList<Game> games;

    public Server() {
        games = new ArrayList<>();
        lastGame = new Game();
        players= new PlayersCollection();

        try (ServerSocket serversocket = new ServerSocket(PORT)) {
            while (true) {
                Player player = players.waitForNewPlayer(serversocket);
                player.registerObserver(this);
                player.startListening();
                //

            }
        }
        catch (IOException e) {
            System.out.println("Server is down");
        }
    }

    @Override
    public void update(Object obj) {
        String message = (String)obj;
        Package pack = new Package(message);
        if (pack.getOperation() != 0) {
            return;
        }
        int id = pack.getSenderID();
        int size = Integer.parseInt(pack.getParameter(1));

        System.out.println("request for game. id:"+id+" size:"+size);
        Player player = players.getPlayer(id);
        player.removeObserver(this);
        lastGame.addParticipate(player);
        if (lastGame.isFull()) {
            lastGame.start(size);
            games.add(lastGame);
            lastGame = new Game();
        }
    }
}
