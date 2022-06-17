package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket socket = null;
    private SimpleServer server;
    private User client = null;

    public ClientThread(Socket socket, SimpleServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Send the response to the oputput stream: server → client
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            while (true) {
                String command = in.readLine();

                System.out.println("Recived command: " + command);
                String response = this.executeCommand(command);

                out.println(response);
                out.flush();
//                String request = in.readLine();
//                String command = this.executeCommand(request);
//                out.println(command);
//                out.flush();
            }


        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public String executeCommand(String command) {
        if (SimpleServer.running) {
            String [] words = command.split(" ");
            if (command.equals("stop")) {
                SimpleServer.running = false;
                return "Server stopped";
            }
            if (words[0].equals("register")) {
                this.register(words[1]);
                return "User registered";
            }
            if (words[0].equals("login")) {
                if (this.login(words[1])) { return "Login successful"; }
                else return "Login failed";
            }
            if (words[0].equals("friend")) {
                if (this.client != null) {
                    this.addFriends(command);
                    return "Friend added";
                } else return "Login first";
            }
            if (words[0].equals("message")) {
                if (this.client != null) {
                    this.sendMessage(words[1]);
                    return "Message sent";
                }else return "Login first";

            }
            if (words[0].equals("read")) {
                if (this.client != null) {
                    System.out.println(words[1]);
                    return this.client.getLastMessage();
                }
                return "Login first";
            }
            else return "comanda primita";
        } else return "Serverul stopped";
    }

    public void register(String name) {
        User newUser = new User(name);
        this.server.addUser(newUser);
    }

    public boolean login(String name) {
        User client = this.findUser(name);
        if (client != null) {
            this.client = client;
        }else return false;
        return true;
    }

    public User findUser(String name) {
        for (User user : this.server.getUsers()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void addFriends(String names) {
        String [] friends = names.split(" ");
        for (String friend : friends) {
            User user = this.findUser(friend);
            if (user != null) {
                this.client.addFriend(user);
            }
        }
    }

    public void sendMessage(String message) {
        for (User user : this.client.getFriends()) {
            user.setLastMessage(message);
        }
    }
}