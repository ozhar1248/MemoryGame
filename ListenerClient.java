package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListenerClient implements Runnable{
    private Socket m_socket;

    public ListenerClient(Socket socket) {
        m_socket = socket;
    }
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            PrintWriter output = new PrintWriter(m_socket.getOutputStream(), true);

            System.out.println("new client arrived");
            output.println("You can speak now");

            while (true) {
                String newLine = input.readLine();
                if (newLine.equals("exit")) {
                    break;
                }
                System.out.println("from client: "+newLine);
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

}
