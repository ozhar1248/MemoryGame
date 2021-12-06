package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Connection implements ISubject, Runnable{
    protected Socket socket;
    protected BufferedReader reader;
    protected PrintWriter writer;
    protected IObserver observer;
    protected String newLine;
    protected boolean listening;


    public Connection(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) {
            try {
                socket.close();
            }
            catch (IOException e2) {
                System.out.println("Unable to close connection");
            }
        }
        listening = true;
    }

    public void send(String message) {
        writer.println(message);
    }

    public void registerObserver(IObserver o) {
        observer = o;
    }

    public void removeObserver(IObserver o) {
        observer = null;
    }

    public void notifyObservers() {
        System.out.println("received: "+newLine);
        observer.update(newLine);
    }

    public void startListening() {
        new Thread(this).start();
    }

    public void stopListening() {
        listening = false;
    }

    public abstract void run();

}
