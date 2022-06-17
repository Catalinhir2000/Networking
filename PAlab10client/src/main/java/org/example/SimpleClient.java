package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleClient {
    public static void main (String[] args) throws IOException {
        boolean running = true;
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        Socket socket = new Socket(serverAddress, PORT);
        PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader (
                new InputStreamReader(socket.getInputStream()));
        try {
            while (running) {
                //send a request to the server
                Scanner scanner = new Scanner(System.in);
                String request = scanner.nextLine();
                if (request.equals("exit")) break;
                out.println(request);

                //receive a response from the server
                String response = in.readLine();
                System.out.println(response);
//                System.out.print("Enter a command: ");
//                Scanner scanner = new Scanner(System.in);
//                String command = scanner.nextLine();
//                if (command.equals("exit")) {
//                    running = false;
//                }
//                out.println(command);
//                String serverResponse = in.readLine();
//                System.out.println(serverResponse);
            }
        }

         catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}