package MemoryGame;

public class Game implements IObserver{
    public static final int NUM_OF_PLAYERS = 2;
    PlayerConnection[] players;

    public Game() {
        players = new PlayerConnection[NUM_OF_PLAYERS];
    }

    public int addParticipate(PlayerConnection player) {
        for (int i=0; i<NUM_OF_PLAYERS; ++i) {
            if (players[i] == null) {
                players[i] = player;
                return 0;
            }
        }
        return -1;
    }

    public void update(Object message) {

    }
}
