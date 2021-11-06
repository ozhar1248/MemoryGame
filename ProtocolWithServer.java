package MemoryGame;

/*
operations:
0 - card chosen. first parameter is card index. example: "0#1 means choose index 1
 */
public class ProtocolWithServer {

    public static Package chooseCard(int index) {
        Package pack =  new Package(0);
        pack.addParameter(""+index);
        return pack;
    }
}
