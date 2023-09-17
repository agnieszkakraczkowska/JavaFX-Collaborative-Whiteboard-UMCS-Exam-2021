package com.example.kolokiwum_ii_2021;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Drawing Server!");
        stage.setWidth(500);
        stage.setHeight(500);
        scene.setFill(Color.WHITE);
        stage.setScene(scene);
        stage.show();

        Controller controller = fxmlLoader.getController();

        Thread serverThread = new Thread(() -> {
            DrawingServer server = new DrawingServer(6000,controller);
            try {
                server.startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
