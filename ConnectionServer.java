package MemoryGame;

import java.io.IOException;
import java.net.Socket;

public class ConnectionServer extends Connection implements Runnable{
    public ConnectionServer(Socket socket) {
        super(socket);
    }

    public void run() {
        try {
            System.out.println("start listening to server");
            while (true) {
                newLine = m_reader.readLine();
                System.out.println("rec: "+newLine);
                notifyObservers();
            }
        }
        catch (IOException e) {
            System.out.println("player disconnected");
            newLine = ProtocolWithServer.exitGame().toString();
            notifyObservers();
        }
        finally {
            try {
                m_socket.close();
            }
            catch (IOException e) {

            }
        }

    }

    public void startListening() {
        new Thread(this).start();
    }
}
