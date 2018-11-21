package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    private static final double mainWidth = 1200;
    private static final double mainHeight = 600;
    private ArrayList<Circle> circles = new ArrayList<>();
    private Optional<Circle> tempCircle = Optional.empty();

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

        canvasHolder.setOnMouseDragged(event -> {
            tempCircle.ifPresent(circle -> {
                circles.remove(circle);
                canvasHolder.getChildren().remove(circle.getCanvas());
                circle.getCanvas().getGraphicsContext2D().clearRect(0, 0, 1215, 600);

                circles.add(circle);

                circle.setCenter(new Point(event.getX(), event.getY()));
                circle.drawCircle();
                canvasHolder.getChildren().add(circle.getCanvas());
            });
        });

        canvasHolder.setOnMousePressed(event -> {
            for (Circle circle: circles) {
                if (Math.abs(event.getX() - circle.getCenter().getX()) < 20 &&
                        Math.abs(event.getY() - circle.getCenter().getY()) < 20) {
                    tempCircle = Optional.of(circle);
                    canvasHolder.getChildren().remove(circle.getCanvas());
                }
            }
        });

        canvasHolder.setOnMouseClicked(event -> {
            if (!tempCircle.isPresent()) {
                Point point = new Point(event.getX(), event.getY());
                Circle circle = new Circle(point, 5);
                circle.drawCircle();
                circles.add(circle);
                canvasHolder.getChildren().add(circle.getCanvas());
            }
        });

        canvasHolder.setOnMouseReleased(event -> {
            if (!tempCircle.isPresent()) {


                if (!circles.isEmpty()) {
                    Circle circle = circles.get(circles.size() - 1);
                    Point center = circle.getCenter();
                    Line line = new Line(center, new Point(event.getX(), event.getY()));
                    line.drawLine();
                    canvasHolder.getChildren().add(line.getCanvas());
                }
            }

            tempCircle = Optional.empty();
        });
    }
}
