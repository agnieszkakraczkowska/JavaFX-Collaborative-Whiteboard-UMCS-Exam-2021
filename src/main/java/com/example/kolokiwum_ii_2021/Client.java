package com.example.kolokiwum_ii_2021;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    Socket socket;
    private PrintWriter writer;

    public Client(String hostname, int port) {
        try {
            this.socket = new Socket(hostname,port);
            this.writer = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String message;
                if ((message = reader.readLine()) != null) {
                    System.out.println("Received message: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToTheServer(String message) {
        writer.println(message);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 6000);
            client.start();

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                client.sendMessageToTheServer(userInput);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
