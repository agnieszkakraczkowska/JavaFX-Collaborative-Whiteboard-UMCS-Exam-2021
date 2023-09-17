package com.example.kolokiwum_ii_2021;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DrawingServer {
    private ServerSocket serverSocket;
    private List<ClientThread> clients = new ArrayList<>();
    private Controller controller;

    public DrawingServer(int port, Controller controller) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.controller = controller;
            System.out.println("Server started on port: " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServer() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client has connected!");
            ClientThread clientThread = new ClientThread(clientSocket, this, this.controller);
            clients.add(clientThread);
            clientThread.start();
        }
    }
}
