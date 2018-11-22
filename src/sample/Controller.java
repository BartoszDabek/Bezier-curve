package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.shape.Circle;
import sample.shape.Line;
import sample.shape.Point;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    private static final double MAIN_WIDTH = 1200;
    private static final double MAIN_HEIGHT = 600;
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private Optional<Circle> tempCircle = Optional.empty();
    private Optional<Line> tempLineStart = Optional.empty();
    private Optional<Line> tempLineEnd = Optional.empty();
    private Optional<Canvas> tempBezier = Optional.empty();
    private Circle lastAddedCircle;
    private static Controller instance;

    @FXML
    private Canvas mainCanvas;
    @FXML
    private StackPane canvasHolder;
    @FXML
    private Label coordinates;

    @FXML
    public void initialize() {
        setInstance(this);
        canvasHolder.setOnMouseMoved(event -> coordinates.setText("X: " + event.getX() + "  Y: " + event.getY()));
        canvasHolder.setMaxHeight(MAIN_HEIGHT);
        canvasHolder.setMaxWidth(MAIN_WIDTH);

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.mainCanvas.getWidth(), this.mainCanvas.getHeight());

        mainCanvas.setHeight(MAIN_HEIGHT);
        mainCanvas.setWidth(MAIN_WIDTH);

        canvasHolder.setOnMouseDragged(event -> {

            drawBezier();


            tempCircle.ifPresent(circle -> {
                circle.setCenter(new Point(event.getX(), event.getY()));
                circle.redraw();

                tempLineStart.ifPresent(line -> {
                    line.setStart(new Point(event.getX(), event.getY()));
                    line.redraw();
                });


                tempLineEnd.ifPresent(line -> {
                    line.setEnd(new Point(event.getX(), event.getY()));
                    line.redraw();
                });
            });
        });

        canvasHolder.setOnMousePressed(event -> {
            if (!tempCircle.isPresent()) {


                for (Circle circle : circles) {
                    if (Math.abs(event.getX() - circle.getCenter().getX()) < 10 &&
                            Math.abs(event.getY() - circle.getCenter().getY()) < 10) {
                        tempCircle = Optional.of(circle);
                    }
                }

                for (Line line : lines) {
                    if (Math.abs(event.getX() - line.getStart().getX()) < 10 &&
                            Math.abs(event.getY() - line.getStart().getY()) < 10) {
                        tempLineStart = Optional.of(line);
                    }

                    if (Math.abs(event.getX() - line.getEnd().getX()) < 10 &&
                            Math.abs(event.getY() - line.getEnd().getY()) < 10) {
                        tempLineEnd = Optional.of(line);
                    }
                }
            }
        });

        canvasHolder.setOnMouseClicked(event -> {
            if (!tempCircle.isPresent()) {
                Point point = new Point(event.getX(), event.getY());
                Circle circle = new Circle(point, 5);
                circle.drawCircle();
                circles.add(circle);
                lastAddedCircle = circle;
            }
            tempCircle = Optional.empty();
        });

        canvasHolder.setOnMouseReleased(event -> {
            if (!tempCircle.isPresent()) {
                if (!circles.isEmpty()) {
                    Point center = lastAddedCircle.getCenter();
                    Line line = new Line(center, new Point(event.getX(), event.getY()));
                    line.drawLine();
                    lines.add(line);
                }
            }

            tempLineStart = Optional.empty();
            tempLineEnd = Optional.empty();
        });
    }

    private void drawBezier() {
        if (circles.size() > 2) {
            if (tempBezier.isPresent()) {
                Canvas canvas = tempBezier.get();
                Controller.getInstance().getCanvasHolder().getChildren().remove(canvas);
                canvas.getGraphicsContext2D().clearRect(0, 0, 1215, 600);
            }

            BezierCurve bezierCurve = new BezierCurve(circles);
            int iterations = 20;
            double[] xPoints = new double[iterations + 1];
            double[] yPoints = new double[iterations + 1];

            double u = 0.0d;
            double m = 1 / (double) iterations;

            for (int i = 0; i <= iterations; i++) {
                xPoints[i] = bezierCurve.xValueAt(u);
                yPoints[i] = bezierCurve.yValueAt(u);
                u += m;
            }

            // Draw curve
            Canvas canvas = new Canvas(1215, 600);
            tempBezier = Optional.of(canvas);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Controller.getInstance().getCanvasHolder().getChildren().add(canvas);

            gc.setStroke(Color.ORANGE);
            gc.strokePolyline(xPoints, yPoints, iterations + 1);
        }
    }

    private static void setInstance(Controller instance) {
        Controller.instance = instance;
    }

    public static Controller getInstance() {
        return instance;
    }

    public StackPane getCanvasHolder() {
        return canvasHolder;
    }
}
