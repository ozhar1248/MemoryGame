package MemoryGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    private static final int PORT = 7777;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String userLine;
            String response = input.readLine();
            System.out.println("from server:  "+response);
            do {
                userLine = scanner.nextLine();
                output.println(userLine);
            } while (!userLine.equals("exit"));
        }
        catch (IOException e) {

        }
    }
}
