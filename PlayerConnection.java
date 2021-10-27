package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerConnection implements Runnable, ISubject{
    private Socket m_socket;
    private BufferedReader m_reader;
    private PrintWriter m_writer;
    private IObserver m_dispatcherToServer;
    private String newLine;


    public PlayerConnection(Socket socket) {
        m_socket = socket;
        try {
            m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            m_writer = new PrintWriter(m_socket.getOutputStream(), true);
        }
        catch (IOException e) {
            System.out.println("client is gone");
        }
        finally {
            try {
                m_socket.close();
            }
            catch (IOException e) {

            }
        }

    }
    public void run() {
        try {

            System.out.println("new client arrived");
            m_writer.println("You can speak now");

            while (true) {
                newLine = m_reader.readLine();
                if (newLine.equals("exit")) {
                    break;
                }
                notifyObservers();
            }
        }
        catch (IOException e) {
            System.out.println("client is gone");
        }
        finally {
            try {
                m_socket.close();
            }
            catch (IOException e) {

            }
        }

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
