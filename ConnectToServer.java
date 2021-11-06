package MemoryGame;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class ConnectToServer implements ISubject, IObserver{
//    private static final int PORT = 7777;
//    Socket socket;
//    PrintWriter output;
//    IObserver observer;
//
//    public ConnectToServer() {
//        observer = null;
//        try {
//            socket = new Socket("localhost", PORT);
//            output = new PrintWriter(socket.getOutputStream(), true);
//            System.out.println("connection with server");
//            new Thread(new ListeningToServer(socket)).start();
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    public void send(String message) {
//        output.println(message);
//    }
//
//    public void registerObserver(IObserver o) {
//        observer = o;
//    }
//    public void removeObserver(IObserver o) {
//        observer = null;
//    }
//    public void notifyObservers() {
//        observer.update();
//    }
//
//    public void update(Object message) {
//
//    }
//
//
//}
