package MemoryGame;

/*

operations:
0 - start game. parameter 1 is the size of the matrix
1 - card chosen. first parameter is card index. example: "0#1 means choose index 1
 */
public class ProtocolWithServer {

    public static Package chooseCard(int index) {
        Package pack =  new Package(1);
        pack.addParameter(""+index);
        return pack;
    }

    public static Package startGame(int size) {
        Package pack =  new Package(0);
        pack.addParameter(""+size);
        return pack;
    }

}
