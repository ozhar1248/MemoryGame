package MemoryGame;

//need to separate between methods for general game and methods for specific game
public class Game implements IObserver{
    public static final int NUM_OF_PLAYERS = 2;
    int currentNumPlayers;
    Player[] players;
    InterpreterClient interpreter;
    int turn;
    GameServerLogic game;

    public Game() {
        players = new Player[NUM_OF_PLAYERS];
        interpreter = new InterpreterClient(this);
        currentNumPlayers = 0;
        turn = 0;

    }

    public int addParticipate(Player player) {
        if (currentNumPlayers >= NUM_OF_PLAYERS) {
            return -1;
        }
        players[currentNumPlayers++] = player;
        if (!isFull()) {
            player.send(ProtocolWithClient.waitForOpponent().toString());
        }
        player.registerObserver(this);
        return 0;
    }

    public boolean isFull() {
        return currentNumPlayers == NUM_OF_PLAYERS;
    }

    public void start(int size) {
        assert players.length == NUM_OF_PLAYERS;
        for (int i=0; i<players.length; ++i) {
            players[i].send(ProtocolWithClient.startGame().toString());
        }

        game = new GameServerLogic(size, players);
        interpreter.setGameLogic(game);
    }

    @Override
    public void update(Object obj) {
        String message = (String)obj;
        if (interpreter != null) {
            interpreter.interpret(message);
        }

    }
}
