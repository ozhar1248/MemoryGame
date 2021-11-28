package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Connection implements ISubject{
    protected Socket m_socket;
    protected BufferedReader m_reader;
    protected PrintWriter m_writer;
    protected IObserver m_dispatcherToServer;
    protected String newLine;


    public Connection(Socket socket) {
        m_socket = socket;
        try {
            m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            m_writer = new PrintWriter(m_socket.getOutputStream(), true);
        }
        catch (IOException e) {
            try {
                m_socket.close();
            }
            catch (IOException e2) {
            }
        }
    }





    public void send(String message) {
        m_writer.println(message);
        System.out.println("sent "+message);
    }

    public void registerObserver(IObserver o) {
        m_dispatcherToServer = o;
    }

    public void removeObserver(IObserver o) {
        m_dispatcherToServer = null;
    }

    public void notifyObservers() {
        m_dispatcherToServer.update(newLine);
    }

    public abstract void startListening() ;


}
