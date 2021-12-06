package MemoryGame;

/*

operations:
0 - start game. parameter 1 is the size of the matrix
1 - card chosen. first parameter is card index. example: "0#1 means choose index 1
2- player disconnected
 */
public class SenderToServer {

    public static String chooseCard(int index) {
        Package pack =  new Package(1);
        pack.addParameter(""+index);
        return pack.toString();
    }

    public static String startGame(int sizeRow, String name) {
        Package pack =  new Package(0);
        pack.addParameter(""+sizeRow);
        pack.addParameter(name);
        return pack.toString();
    }

    public static String exitGame() {
        Package pack = new Package(2);
        return pack.toString();
    }

}
