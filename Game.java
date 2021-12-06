package MemoryGame;

// No need to lock critical section because the client side takes care that no requests arrive simultaneously
// from different players
public class Game implements IObserver{
    int currentNumPlayers;
    int level;
    Player[] players;
    InterpreterClient interpreter;
    GameServerLogic logic;
    IGamesStarter gamesOrganizer;

    public Game(IGamesStarter gamesOrganizer) {
        this.gamesOrganizer = gamesOrganizer;
        this.level = -1;
        players = new Player[MemoryGame.NUM_OF_PLAYERS_IN_GAME];
        interpreter = new InterpreterClient(this);
        currentNumPlayers = 0;
    }

    public int addParticipate(Player player) {
        if (currentNumPlayers >= MemoryGame.NUM_OF_PLAYERS_IN_GAME) {
            return -1;
        }
        for (int i=0; i<players.length; ++i) {
            if (players[i]==null) {
                players[i] = player;
                currentNumPlayers++;
                break;
            }
        }
        player.registerObserver(this);
        if (!isFull()) {
            player.send(SenderToClient.waitForOpponent());
            return 0;
        }

        return -1;
    }

    public boolean isFull() {
        return currentNumPlayers == MemoryGame.NUM_OF_PLAYERS_IN_GAME;
    }

    public void start(int level) {
        assert players.length == MemoryGame.NUM_OF_PLAYERS_IN_GAME;

        this.level = level;
        for (int i=0; i<players.length; ++i) {
            players[i].send(SenderToClient.startGame());
        }

        logic = new GameServerLogic(level, players);
        interpreter.setGameLogic(logic);
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

    public void endGame() {
        for (int i=0; i<players.length; ++i) {
            players[i].removeObserver(this);
        }
        gamesOrganizer.clearGame(this);
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getLevel() {
        return level;
    }
}
