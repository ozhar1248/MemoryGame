package MemoryGame;

/*
0- wait for opponent
1- start game
2- turn on.
3- flip card. parameter 1 - card index. parameter 2 - card value
4- flip permanently 2 cards. par 1 is card 1 index. par 2 is card 2 index. par 3 is cards value
 */
public class ProtocolWithClient {
    public static Package waitForOpponent() {
        return new Package(0);
    }
    public static Package startGame() {
        return new Package(1);
    }
    public static Package changeTurn() {
        return new Package(2);
    }

    public static Package flipCard(int cardIndex, int cardValue) {
        Package pack = new Package(3);
        pack.addParameter(""+cardIndex);
        pack.addParameter(""+cardValue);
        return pack;
    }

    public static Package flipCardsPermanently(int cardIndex1, int cardIndex2, int value) {
        Package pack = new Package(4);
        pack.addParameter(""+cardIndex1);
        pack.addParameter(""+cardIndex2);
        pack.addParameter(""+value);
        return pack;
    }


}
