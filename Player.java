package MemoryGame;

import java.net.Socket;
import java.util.ArrayList;

public class Player implements IObserver, ISubject{
    private int id;
    private Connection connectionWithUser;
    private ArrayList<IObserver> observers;
    String newMessage;

    public Player(Socket socket, int id) {
        observers = new ArrayList<>();
        this.id = id;
        connectionWithUser = new Connection(socket);
        connectionWithUser.registerObserver(this);
    }

    public int getId() {
        return id;
    }

    public void startListening() {
        connectionWithUser.startListening();
    }

    public void send(String message) {
        connectionWithUser.send(message);
    }


    @Override
    public void update(Object message) {
        newMessage = (String)message;
        Package pack = new Package(newMessage);
        pack.setSenderID(id);
        newMessage = pack.toString();
        notifyObservers();
    }

    @Override
    public void registerObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (int i=0; i<observers.size(); ++i) {
            observers.get(i).update(newMessage);
        }
    }
}
