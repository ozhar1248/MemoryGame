package MemoryGame;


public class InterpreterClient {
    private Game m_game;
    private GameServerLogic gameLogic;

    public InterpreterClient(Game game) {
        this.m_game = game;
    }

    public void interpret(String message) {
        Package pack = new Package(message);
        int id = pack.getSenderID();
        if (pack.getOperation()==1) {
            gameLogic.cardChosen(id, Integer.parseInt(pack.getParameter(1))); //add this method to gameLogic
        }
        if (pack.getOperation()==2) {
            if (!m_game.isFull()) {
                m_game.removePlayer(id);
                return;
            }
            gameLogic.exit(id);
        }
    }

    public void setGameLogic(GameServerLogic logic) {
        gameLogic = logic;
    }
}
