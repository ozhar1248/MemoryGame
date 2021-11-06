package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient implements IObserver{
    private static final int PORT = 7777;
    Connection connection;
    GameClientLogic game; /*consider making interface*/
    ProtocolWithServer protocol;
    Socket socket;
    InterpretServer interperter; /*consider making interface*/

    public GameClient() {
        try {
            socket = new Socket("localhost", PORT);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        connection = new Connection(socket);
        connection.registerObserver(this);
        game = new GameClientLogic(connection);
        interperter = new InterpretServer(game);
        connection.startListening(); /*think about it*/

    }

    public void update(Object message) {

        interperter.interpret((String)message);
    }
}
