package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable, ISubject{
    private Socket m_socket;
    private BufferedReader m_reader;
    private PrintWriter m_writer;
    private IObserver m_dispatcherToServer;
    private String newLine;


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

    public void startListening() {
        new Thread(this).start();
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
            System.out.println(e.getMessage());
        }
        finally {
            try {
                m_socket.close();
            }
            catch (IOException e) {

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


}
