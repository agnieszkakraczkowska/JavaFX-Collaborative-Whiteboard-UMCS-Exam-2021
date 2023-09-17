package com.example.kolokiwum_ii_2021;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    @FXML
    private Label offsetLabel;

    @FXML
    private Canvas drawingCanvas;

    private GraphicsContext gc;
    private static Map<Line, Color> lines = new HashMap<>();
    private double labelOffsetX = 0;
    private double labelOffsetY = 0;

    @FXML
    private void initialize() {
        drawingCanvas.setFocusTraversable(true);
        gc = drawingCanvas.getGraphicsContext2D();

        drawingCanvas.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            if (keyCode == KeyCode.UP) {
                labelOffsetY -= 10;
                updateLinesCoordinates(0,-10);
            } else if (keyCode == KeyCode.DOWN) {
                labelOffsetY += 10;
                updateLinesCoordinates(0,10);
            } else if (keyCode == KeyCode.LEFT) {
                labelOffsetX -= 10;
                updateLinesCoordinates(-10,0);
            } else if (keyCode == KeyCode.RIGHT) {
                labelOffsetX += 10;
                updateLinesCoordinates(10,0);
            } else {
                return;
            }
            updateOffsetLabel();
        });
        updateOffsetLabel();
    }

    private void updateLinesCoordinates(double offsetX, double offsetY) {
        gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        for (var currentLine : lines.keySet()) {
            double startX = currentLine.getStartX() + offsetX;
            double startY = currentLine.getStartY() + offsetY;
            double endX = currentLine.getEndX() + offsetX;
            double endY = currentLine.getEndY() + offsetY;
            currentLine.setStartX(startX);
            currentLine.setStartY(startY);
            currentLine.setEndX(endX);
            currentLine.setEndY(endY);

            gc.setStroke(lines.get(currentLine));
            gc.strokeLine(startX, startY, endX, endY);
        }
    }

    @FXML
    public void drawLine(double x1, double y1, double x2, double y2, Color color) {
        gc.setStroke(color);
        gc.strokeLine(x1, y1, x2, y2);
        lines.put(new Line(x1, y1, x2, y2), color);
    }

    private void updateOffsetLabel() {
        offsetLabel.setText("Offset: X=" + labelOffsetX + ", Y=" + labelOffsetY);
    }
}
