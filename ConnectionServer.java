package MemoryGame;

import java.io.IOException;
import java.net.Socket;

public class ConnectionServer extends Connection{

    public ConnectionServer(Socket socket) {
        super(socket);
        listening = true;
    }

    public void run() {
        try {
            while (listening) {
                newLine = reader.readLine();
                notifyObservers();
            }
        }
        catch (IOException e) {
            newLine = SenderToServer.exitGame();
            notifyObservers();
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.out.println("Unable to close connection");
            }
        }
    }


}
