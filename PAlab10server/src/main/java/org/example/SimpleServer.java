package org.example;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer {
    // Define the port on which the server is listening
    public static final int PORT = 8100;
    public static boolean running = true;
    private List<User> users = new ArrayList<>();

    public SimpleServer() throws IOException {
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket ( PORT );
            System.out.println ( "Server started on port " + PORT );
            while ( running ) {
                if (running) {
                    System.out.println("Waiting for client on port " + PORT);
                    Socket clientSocket = serverSocket.accept();

                    new ClientThread(clientSocket, this).start();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
        }
        finally {
            serverSocket.close();
        }


    }
    public void addUser(User user){
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public static void main (String [] args ) throws IOException {
        SimpleServer server = new SimpleServer ();
    }


}