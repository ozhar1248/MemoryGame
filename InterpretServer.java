package MemoryGame;

public class InterpretServer implements IInterpreter{
    GameClientLogic game;

    public InterpretServer(GameClientLogic game) {
        this.game = game;
    }

    public void interpret(String s) {
        Package messPackage = new Package(s);
        int cardValue;
        int indexPlayer;
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
                indexPlayer = Integer.parseInt(messPackage.getParameter(4));
                game.flipCardPermanently(cardIndex1, cardIndex2, cardValue, indexPlayer);
                return;
            case 6:
                int numberOfPlayers = Integer.parseInt(messPackage.getParameter(1));
                String[] names = new String[numberOfPlayers];
                for (int i=0; i<numberOfPlayers; ++i) {
                    names[i] = messPackage.getParameter(i+2);
                }
                game.updateNames(names);
                return;
            case 7:
                indexPlayer = Integer.parseInt(messPackage.getParameter(1));
                game.playersDisconnected(indexPlayer);
                return;
            case 8:

        }
    }
}
