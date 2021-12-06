package MemoryGame;

import java.io.IOException;
import java.net.Socket;

public class ConnectionClient extends Connection{

    public ConnectionClient(Socket socket) {
        super(socket);
    }

    public void run() {
        try {
            while (listening) {
                newLine = reader.readLine();
                if (newLine == null) {
                    break;
                }
                notifyObservers();
            }
        }
        catch (IOException e) {
            newLine = SenderToClient.exit(-1);
            notifyObservers();
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void startListening() {
        new Thread(this).start();
    }
}
