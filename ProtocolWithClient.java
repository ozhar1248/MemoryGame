package MemoryGame;

/*
0- wait for opponent
1- start game
2- turn on.
3- flip card. parameter 1 - card index. parameter 2 - card value
4- flip permanently 2 cards. par 1 is card 1 index. par 2 is card 2 index. par 3 is cards value
5- update points. parameter 1 is the index of player, according to the names array that was sent earlier.
   parameter 2 is the new points
6- inform about number of players. par 1 is number of players. then next parameters are the player names. The player
    will receive its name as "You"
7- exit. par 1 is the index of the player who exited
 */
public class ProtocolWithClient {
    public static String waitForOpponent() {
        return new Package(0).toString();
    }
    public static String startGame() {
        return new Package(1).toString();
    }
    public static String changeTurn() {
        return new Package(2).toString();
    }

    public static String informNames(String[] names) {
        Package pack = new Package(6);
        pack.addParameter(""+names.length);
        for (int i=0; i< names.length; ++i) {
            pack.addParameter(names[i]);
        }
        return pack.toString();
    }

    public static String flipCard(int cardIndex, int cardValue) {
        Package pack = new Package(3);
        pack.addParameter(""+cardIndex);
        pack.addParameter(""+cardValue);
        return pack.toString();
    }

    public static String flipCardsPermanently(int cardIndex1, int cardIndex2, int value) {
        Package pack = new Package(4);
        pack.addParameter(""+cardIndex1);
        pack.addParameter(""+cardIndex2);
        pack.addParameter(""+value);
        return pack.toString();
    }

    public static String updatePoints(int index, int points) {
        Package pack = new Package(5);
        pack.addParameter(""+index);
        pack.addParameter(""+points);
        return pack.toString();
    }

    public static String exit(int index) {
        Package pack = new Package(7);
        pack.addParameter(""+index);
        return pack.toString();
    }

}
