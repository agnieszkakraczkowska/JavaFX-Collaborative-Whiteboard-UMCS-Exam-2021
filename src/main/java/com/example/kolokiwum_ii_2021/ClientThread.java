package com.example.kolokiwum_ii_2021;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private DrawingServer server;
    private Color strokeColor = Color.BLACK;
    private Controller controller;

    public ClientThread(Socket clientSocket, DrawingServer server, Controller controller) {
        this.socket = clientSocket;
        this.server = server;
        this.controller = controller;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while((message = reader.readLine()) != null) {
                String[] messageParts = message.split(" ");
                if(messageParts.length == 1 && isAValidColor(messageParts[0])) {
                    strokeColor = Color.valueOf(messageParts[0]);
                }
                else if (messageParts.length == 4 && ifAllPartsAreNumbers(messageParts)){
                    double x1 = Double.parseDouble(messageParts[0]);
                    double y1 = Double.parseDouble(messageParts[1]);
                    double x2 = Double.parseDouble(messageParts[2]);
                    double y2 = Double.parseDouble(messageParts[3]);
                    controller.drawLine(x1, y1, x2, y2, strokeColor);
                }
                else {
                    writer.println("Invalid message format!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ifAllPartsAreNumbers(String[] messageParts) {
        for (String part : messageParts) {
            if (!part.matches("[0-9]+(\\.[0-9]+)?")) {
                return false;
            }
        }
        return true;
    }

    private boolean isAValidColor(String message) {
        return message.matches("[a-fA-F0-9]{6}");
    }

}
