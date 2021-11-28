package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements IObserver{

    private PlayersCollection players;
    private Game[] newGames;
    private ArrayList<Game>[] gamesCollections;

    public Server() {
        gamesCollections = new ArrayList[MemoryGame.NUM_OF_LEVELS];
        newGames = new Game[MemoryGame.NUM_OF_LEVELS];
        for (int i=0; i<MemoryGame.NUM_OF_LEVELS; ++i) {
            gamesCollections[i] = new ArrayList<>();
            newGames[i] = new Game();
        }

        players= new PlayersCollection();

        try (ServerSocket serversocket = new ServerSocket(MemoryGame.PORT)) {
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
        int sizeRow = Integer.parseInt(pack.getParameter(1));
        String name = pack.getParameter(2);

        Player player = players.getPlayer(id);
        player.setName(name);
        player.removeObserver(this);
        if (sizeRow == MemoryGame.SIZE_ROW_EASY) {
            addParticipant(MemoryGame.LEVEL1, sizeRow, player);
        }
        else if (sizeRow == MemoryGame.SIZE_ROW_MEDIUM) {
            addParticipant(MemoryGame.LEVEL2, sizeRow, player);
        }
        else if (sizeRow == MemoryGame.SIZE_ROW_HARD) {
            addParticipant(MemoryGame.LEVEL3, sizeRow, player);
        }
    }

    public void addParticipant(int level, int sizeRow, Player player) {
        newGames[level-1].addParticipate(player);
        if (newGames[level-1].isFull()) {
            newGames[level-1].start(sizeRow);
            gamesCollections[level-1].add(newGames[level-1]);
            newGames[level-1] = new Game();
        }
    }
}
