package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionClient extends Connection implements Runnable{
    public ConnectionClient(Socket socket) {
        super(socket);
    }

    public void run() {
        try {
            System.out.println("start listening to client");
            while (true) {
                newLine = m_reader.readLine();
                System.out.println("rec: "+newLine);
                notifyObservers();
            }
        }
        catch (IOException e) {
            System.out.println("server disconnected");
            newLine = ProtocolWithClient.exit(0);
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
