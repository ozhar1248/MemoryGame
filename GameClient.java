package MemoryGame;

import java.io.IOException;
import java.net.Socket;

public class GameClient{
    ConnectionClient connection;
    GameClientLogic game; /*consider making interface*/
    Socket socket;
    GUManagement gui;

    public GameClient() {
        gui = new GUManagement();
        gui.openHomeWindow(this);
    }

    public void connectToServer(String name, int level) {
        try {
            socket = new Socket("localhost", MemoryGame.PORT);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        connection = new ConnectionClient(socket);
        game = new GameClientLogic(connection, gui, name, level);
    }

}
