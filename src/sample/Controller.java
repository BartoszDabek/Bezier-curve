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
import sample.shape.bezier.BezierChain;
import sample.shape.bezier.BezierCurve;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private static final double MAIN_WIDTH = 1200;
    private static final double MAIN_HEIGHT = 600;
    private static final int CIRCLE_RADIUS = 5;
    private List<Circle> canvasCircles = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private Optional<Circle> tempCircle = Optional.empty();
    private Optional<Line> tempLineStart = Optional.empty();
    private Optional<Line> tempLineEnd = Optional.empty();
    private static Controller instance;
    private BezierChain bezierChain = new BezierChain();

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


                for (Circle circle : canvasCircles) {
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
                Circle circle = new Circle(point, CIRCLE_RADIUS);
                circle.drawCircle();
                canvasCircles.add(circle);
                bezierChain.createCurveIfPossible(circle);
            }
            tempCircle = Optional.empty();
        });

        canvasHolder.setOnMouseReleased(event -> {
            if (!tempCircle.isPresent()) {
                if (!canvasCircles.isEmpty()) {
                    Point center = canvasCircles.get(canvasCircles.size() - 1).getCenter();
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
        if (!bezierChain.getBezierCurves().isEmpty()) {
            for (int i=1; i<= bezierChain.getBezierCurves().size(); i++) {
                BezierCurve tempBezier = bezierChain.getBezierCurves().get(i-1);
                BezierCurve newCurve = tempBezier.newCurve(i, canvasCircles);
                tempBezier.remove();
                bezierChain.getBezierCurves().add(i-1, newCurve);
                bezierChain.getBezierCurves().remove(tempBezier);
                newCurve.draw();
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
