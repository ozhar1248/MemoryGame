package MemoryGame;

import java.util.concurrent.locks.ReentrantLock;

public class InterpreterClient implements IInterpreter{
    private Game game;
    private GameServerLogic gameLogic;
    private ReentrantLock lock;

    public InterpreterClient(Game game) {
        this.game = game;
        lock = new ReentrantLock();
    }

    public void interpret(String message) {
        assert gameLogic != null;

        Package pack = new Package(message);
        int id = pack.getSenderID();
        int operation = pack.getOperation();
        switch (operation) {
            case 1:
                gameLogic.cardChosen(id, Integer.parseInt(pack.getParameter(1)));
                if (gameLogic.isFinished()) {
                    //also ask who is the winner
                    game.endGame();
                }
                break;
            case 2:
                lock.lock();
                try {
                    if (game == null) {
                        return;
                    }
                    if (!game.isFull()) {
                        game.removePlayer(id);
                        return;
                    }
                    gameLogic.exit(id);
                    game.endGame();
                    break;
                }
                finally {
                    lock.unlock();
                }

        }
    }

    public static void waitForStartingGame(String message, IGamesStarter starter) {
        Package pack = new Package(message);
        if (pack.getOperation() != 0) {
            return;
        }
        int id = pack.getSenderID();
        int level = Integer.parseInt(pack.getParameter(1));
        String name = pack.getParameter(2);
        starter.findGameForPlayer(id, level, name);
    }

    public void setGameLogic(GameServerLogic logic) {
        gameLogic = logic;
    }
}
