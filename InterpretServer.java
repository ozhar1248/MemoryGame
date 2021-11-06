package MemoryGame;

public class InterpretServer {
    GameClientLogic game;

    public InterpretServer(GameClientLogic game) {
        this.game = game;
    }

    public void interpret(String s) {
        Package messPackage = ProtocolWithClient.getPackage(s);
        if ( messPackage.getOperation() == 0) {
            game.waitForOpponents();
            return;
        }
        if (messPackage.getOperation() == 1) {
            game.startGame();
        }
        if (messPackage.getOperation() == 2) {
            game.changeTurn();
        }
    }
}
