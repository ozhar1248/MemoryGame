package MemoryGame;

import java.net.Socket;

public class Player implements IObserver, ISubject{
    private int id;
    private String name;
    private ConnectionServer connectionWithUser;
    private IObserver observer;
    String newMessage;

    public Player(Socket socket, int id) {
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

    public void stopListening() {
        connectionWithUser.stopListening();
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
        observer = o;
    }

    @Override
    public void removeObserver(IObserver o) {
        observer = null;
    }

    @Override
    public void notifyObservers() {
        if (observer == null) {
            return;
        }
        observer.update(newMessage);
    }
}
