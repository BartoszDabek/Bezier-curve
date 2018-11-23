package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.shape.BezierCurve;
import sample.shape.Circle;
import sample.shape.Line;
import sample.shape.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private static final double MAIN_WIDTH = 1200;
    private static final double MAIN_HEIGHT = 600;
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Circle> tempCircles = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<BezierCurve> bezierCurves = new ArrayList<>();
    private Optional<Circle> tempCircle = Optional.empty();
    private Optional<Line> tempLineStart = Optional.empty();
    private Optional<Line> tempLineEnd = Optional.empty();
    private Optional<BezierCurve> tempBezier = Optional.empty();
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
            coordinates.setText("X: " + event.getX() + "  Y: " + event.getY());
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
                tempCircles.add(circle);
                lastAddedCircle = circle;

                if (tempCircles.size() > 3) {
                    BezierCurve bezierCurve = new BezierCurve(tempCircles);
                    bezierCurves.add(bezierCurve);
                    bezierCurve.draw();
                    Circle circle1 = tempCircles.get(3);
                    tempCircles = new ArrayList<>();
                    tempCircles.add(circle1);
                }
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
        if (!bezierCurves.isEmpty()) {
            tempBezier.ifPresent(BezierCurve::remove);
            ArrayList<Circle> temp;
            BezierCurve tempBezier;
            for (int i=1; i<= bezierCurves.size(); i++) {
                temp = new ArrayList<>();
                tempBezier = bezierCurves.get(i-1);
                for (int j=(i*3)-3;j<((i*3)-3) + 4; j++) {
                    if (j < circles.size()) {
                        Circle circle = circles.get(j);
                        temp.add(circle);
                    }
                }

                BezierCurve bezier2 = new BezierCurve(temp);
                tempBezier.remove();
                bezierCurves.add(i-1, bezier2);
                bezierCurves.remove(tempBezier);
                bezier2.draw();
            }
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
