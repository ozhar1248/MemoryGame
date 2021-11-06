package MemoryGame;

/*
0- wait for opponent
1- start game
2- update turn. parameter integer. example: "2#1" means player 1 turn
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
    public static Package getPackage(String message) {
        return new Package(message);
    }


}
