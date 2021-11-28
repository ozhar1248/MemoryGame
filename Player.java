package MemoryGame;

import java.net.Socket;
import java.util.ArrayList;

public class Player implements IObserver, ISubject{
    private int id;
    private String name;
    private ConnectionServer connectionWithUser;
    private ArrayList<IObserver> observers;
    String newMessage;

    public Player(Socket socket, int id) {
        observers = new ArrayList<>();
        this.id = id;
        connectionWithUser = new ConnectionServer(socket);
        connectionWithUser.registerObserver(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void startListening() {
        connectionWithUser.startListening();
    }

    public void send(String message) {
        connectionWithUser.send(message);
    }

    public void setName(String name) {
        this.name = name;
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
