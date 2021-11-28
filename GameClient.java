package MemoryGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class GameClient implements IObserver{
    private static final int PORT = 7777;
    ConnectionClient connection;
    GameClientLogic game; /*consider making interface*/
    ProtocolWithServer protocol;
    Socket socket;
    InterpretServer interpreter; /*consider making interface*/
    private JFrame homeWindow;

    public GameClient() {
        homeWindow = new  HomeWindow(this);
        homeWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });


    }

    public void update(Object message) {

        interpreter.interpret((String)message);
    }

    public void connectToServer(String name, int level) {
        try {
            socket = new Socket("localhost", PORT);

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        connection = new ConnectionClient(socket);
        connection.registerObserver(this);
        homeWindow.setVisible(false);
        game = new GameClientLogic(this, connection, name, level);
        interpreter = new InterpretServer( game);
        connection.startListening(); /*think about it*/
    }

    public void exitGame() {
        homeWindow.setVisible(true);
    }
}
