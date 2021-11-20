package MemoryGame;

public class InterpretServer {
    GameClientLogic game;

    public InterpretServer(GameClientLogic game) {
        this.game = game;
    }

    public void interpret(String s) {
        Package messPackage = new Package(s);
        int cardValue;
        switch (messPackage.getOperation()) {
            case 0:
                game.waitForOpponents();
                return;
            case 1:
                game.startGame();
                return;
            case 2:
                game.turnOn();
                return;
            case 3:
                int cardIndex = Integer.parseInt(messPackage.getParameter(1));
                cardValue = Integer.parseInt(messPackage.getParameter(2));
                game.flipCard(cardIndex, cardValue);
                return;
            case 4:
                int cardIndex1 = Integer.parseInt(messPackage.getParameter(1));
                int cardIndex2 = Integer.parseInt(messPackage.getParameter(2));
                cardValue = Integer.parseInt(messPackage.getParameter(3));
                game.flipCardPermanently(cardIndex1, cardIndex2, cardValue);
                return;
        }
    }
}
