package MemoryGame;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class GamesOrganizer implements IObserver, IGamesStarter{
    private final Game[] newGames;
    private final ArrayList<Game>[] gamesCollections;
    private final PlayersCollection players;
    private final ReentrantLock lock;

    public GamesOrganizer(PlayersCollection players) {
        this.players = players;
        lock = new ReentrantLock();

        gamesCollections = new ArrayList[MemoryGame.Sizes.length];
        newGames = new Game[MemoryGame.Sizes.length];

        for (int i=0; i<MemoryGame.Sizes.length; ++i) {
            gamesCollections[i] = new ArrayList<>();
            newGames[i] = new Game(this);
        }
    }

    public void addNewPlayer(Player player) {
        player.registerObserver(this);
        player.startListening();
    }

    @Override
    public void update(Object obj) {
        String message = (String)obj;
        InterpreterClient.waitForStartingGame(message,this);
    }

    @Override
    public void findGameForPlayer(int id, int level, String name) {
        Player player = players.getPlayer(id);
        player.setName(name);

        lock.lock();
        try {
            if (newGames[level].addParticipate(player) < 0) {
                newGames[level].start(level);
                gamesCollections[level].add(newGames[level]);
                newGames[level] = new Game(this);
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void clearGame(Game game) {
        lock.lock();
        try {
            removePlayers(game.getPlayers());
            int level = game.getLevel();
            assert level>=0;
            gamesCollections[level].remove(game);
        }
        finally {
            lock.unlock();
        }
    }

    private void removePlayers(Player[] players) {
        for (int i=0; i<players.length; ++i) {
            players[i].stopListening();
            this.players.removePlayer(players[i]);
        }
    }
}
