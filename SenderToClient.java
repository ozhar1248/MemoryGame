package MemoryGame;

/*
0- wait for opponent
1- start game
2- turn on.
3- flip card. parameter 1 - card index. parameter 2 - card value
4- flip permanently 2 cards. par 1 is card 1 index. par 2 is card 2 index. par 3 is cards value
5- update points. parameter 1 is the index of player, according to the names array that was sent earlier.
   parameter 2 is the new points- CANCELLED
6- inform about names of players. par 1 is number of players. then next parameters are the player names
7- exit. par 1 is the index of the player who exited
 */
public class SenderToClient {
    private static final int WAIT_FOR_OPPONENT = 0;
    private static final int START_GAME = 1;
    private static final int TURN_ON = 2;
    private static final int FLIP_CARD = 3;
    private static final int FLIP_PERMANENTLY = 4;
    private static final int UPDATE_POINTS = 5;
    private static final int INFORM_NAMES = 6;
    private static final int EXIT = 7;

    public static String waitForOpponent() {
        return new Package(WAIT_FOR_OPPONENT).toString();
    }

    public static String startGame() {
        return new Package(START_GAME).toString();
    }

    public static String turnOn() {
        return new Package(TURN_ON).toString();
    }

    public static String informNames(String[] names) {
        Package pack = new Package(INFORM_NAMES);
        pack.addParameter(""+names.length);
        for (int i=0; i< names.length; ++i) {
            pack.addParameter(names[i]);
        }
        return pack.toString();
    }

    public static String flipCard(int cardIndex, int cardValue) {
        Package pack = new Package(FLIP_CARD);
        pack.addParameter(""+cardIndex);
        pack.addParameter(""+cardValue);
        return pack.toString();
    }

    public static String flipCardsPermanently(int cardIndex1, int cardIndex2, int value, int indexPlayer) {
        Package pack = new Package(FLIP_PERMANENTLY);
        pack.addParameter(""+cardIndex1);
        pack.addParameter(""+cardIndex2);
        pack.addParameter(""+value);
        pack.addParameter(""+indexPlayer);
        return pack.toString();
    }

    public static String exit(int index) {
        Package pack = new Package(EXIT);
        pack.addParameter(""+index);
        return pack.toString();
    }

}
