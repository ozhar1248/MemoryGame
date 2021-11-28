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
        for (int i=0; i<players.length; ++i) {
            if (players[i]==null) {
                players[i] = player;
                currentNumPlayers++;
                break;
            }
        }
        if (!isFull()) {
            player.send(ProtocolWithClient.waitForOpponent().toString());
        }
        player.registerObserver(this);
        return 0;
    }

    public boolean isFull() {
        return currentNumPlayers == NUM_OF_PLAYERS;
    }

    public void start(int sizeRow) {
        assert players.length == NUM_OF_PLAYERS;
        for (int i=0; i<players.length; ++i) {
            players[i].send(ProtocolWithClient.startGame().toString());
        }

        game = new GameServerLogic(sizeRow, players);
        interpreter.setGameLogic(game);
    }

    public void removePlayer(int id) {
        for (int i=0; i<players.length; ++i) {
            if (players[i].getId() == id) {
                players[i].removeObserver(this);
                players[i] = null;
                currentNumPlayers--;
                return;
            }
        }
    }
    @Override
    public void update(Object obj) {
        String message = (String)obj;
        if (interpreter != null) {
            interpreter.interpret(message);
        }

    }
}
