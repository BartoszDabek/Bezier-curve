package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    private static final double mainWidth = 1200;
    private static final double mainHeight = 600;
    private ArrayList<Circle> circles = new ArrayList<>();

    @FXML
    private Canvas mainCanvas;
    @FXML
    private StackPane canvasHolder;
    @FXML
    private Label coordinates;

    @FXML
    public void initialize() {
        canvasHolder.setOnMouseMoved(event -> coordinates.setText("X: " + event.getX() + "  Y: " + event.getY()));
        canvasHolder.setMaxHeight(mainHeight);
        canvasHolder.setMaxWidth(mainWidth);

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.mainCanvas.getWidth(), this.mainCanvas.getHeight());

        mainCanvas.setHeight(mainHeight);
        mainCanvas.setWidth(mainWidth);

        canvasHolder.setOnMouseClicked(event -> {
            gc.beginPath();
            for (Circle circle: circles) {
                Point center = circle.getCenter();
                gc.lineTo(center.getX(), center.getY());
            }
            gc.stroke();
        });

        canvasHolder.setOnMouseReleased(event -> {
            Point point = new Point(event.getX(), event.getY());
            Circle circle = new Circle(point, 5);
            circle.drawCircle(gc);
            circles.add(circle);
        });
    }
}
