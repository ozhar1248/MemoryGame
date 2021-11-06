//package MemoryGame;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//public class ListeningToServer implements Runnable, ISubject {
//    private BufferedReader input;
//    private IObserver observer;
//    private String message;
//
//    public ListeningToServer(Socket socket) {
//        try {
//            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void run() {
//        while (true) {
//            try {
//                message = input.readLine();
//                notifyObservers();
//            }
//            catch (IOException e) {
//                System.out.println(e.getMessage());
//                return;
//            }
//
//        }
//    }
//
//    public void registerObserver(IObserver o) {
//        observer = o;
//    }
//    public void removeObserver(IObserver o) {
//        observer = null;
//    }
//    public void notifyObservers() {
//        observer.update(message);
//    }
//}
