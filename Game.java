package MemoryGame;

public class Game {
    public static final int NUM_OF_PLAYERS = 2;
    int currentNumPlayers;
    Connection[] players;
    InterperterClient[] interpreters;
    int turn;

    public Game() {
        players = new Connection[NUM_OF_PLAYERS];
        interpreters = new InterperterClient[NUM_OF_PLAYERS];
        currentNumPlayers = 0;
        turn = 0;
    }

    public int addParticipate(Connection player) {
        if (currentNumPlayers >= NUM_OF_PLAYERS) {
            return -1;
        }
        players[currentNumPlayers] = player;
        interpreters[currentNumPlayers] = new InterperterClient(player,currentNumPlayers, this);
        currentNumPlayers++;
        if (!isFull()) {
            player.send(ProtocolWithClient.waitForOpponent().toString());
        }
        return 0;
    }

    public boolean isFull() {
        return currentNumPlayers == NUM_OF_PLAYERS;
    }

    public void start() {
        assert players.length == NUM_OF_PLAYERS;
        for (int i=0; i<players.length; ++i) {
            players[i].send(ProtocolWithClient.startGame().toString());
        }
        players[0].send(ProtocolWithClient.changeTurn().toString());
    }

    public void cardChosen(int card, int player) {
        System.out.println("player "+player+" chose "+card);
    }

}
