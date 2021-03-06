package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.shape.Circle;
import sample.shape.Line;
import sample.shape.Point;
import sample.shape.bezier.BezierChain;
import sample.shape.bezier.BezierCurve;
import sample.shape.util.ShapeSelector;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final double MAIN_WIDTH = 1200;
    private static final double MAIN_HEIGHT = 600;
    private static Controller instance;
    private List<Circle> circles = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private ShapeSelector shapeSelector = new ShapeSelector();
    private BezierChain bezierChain = new BezierChain();

    @FXML
    private Canvas mainCanvas;
    @FXML
    private StackPane canvasHolder;
    @FXML
    private Label coordinates;
    @FXML
    private TextField newPointX, newPointY;
    @FXML
    private TextField pointPosition, modifyPointX, modifyPointY;


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
            redrawPoint(event.getX(), event.getY());
        });

        canvasHolder.setOnMousePressed(event -> {
            if (!shapeSelector.isCirclePresent()) {
                shapeSelector.setCircle(circles, event);
                shapeSelector.setLines(lines, event.getX(), event.getY());
            }
        });

        canvasHolder.setOnMouseClicked(event -> {
            if (!shapeSelector.isCirclePresent()) {
                addPointWithLineToCanvas(event.getX(), event.getY());
            }
            shapeSelector.setCircleToEmpty();
            shapeSelector.setLinesToEmpty();
        });
    }

    private void drawBezier() {
        if (!bezierChain.getBezierCurves().isEmpty()) {
            for (int i = 1; i <= bezierChain.getBezierCurves().size(); i++) {
                BezierCurve tempBezier = bezierChain.getBezierCurves().get(i - 1);
                BezierCurve newCurve = tempBezier.newCurve(i, circles);
                tempBezier.remove();
                bezierChain.getBezierCurves().add(i - 1, newCurve);
                bezierChain.getBezierCurves().remove(tempBezier);
                newCurve.draw();
            }
        }
    }

    private void redrawPoint(double x, double y) {
        shapeSelector.getCircle().ifPresent(circle -> {
            circle.setCenter(new Point(x, y));
            circle.redraw();

            shapeSelector.getStartingLine().ifPresent(line -> {
                line.setStart(new Point(x, y));
                line.redraw();
            });

            shapeSelector.getEndLine().ifPresent(line -> {
                line.setEnd(new Point(x, y));
                line.redraw();
            });
        });
    }

    private void addPointWithLineToCanvas(double x, double y) {
        Point point = new Point(x, y);
        Circle circle = new Circle(point,circles.size() + 1);
        circle.drawCircle();
        circles.add(circle);
        bezierChain.createCurveIfPossible(circle);

        if (circles.size() > 1) {
            addLineToCanvas(circle.getCenter());
        }
    }

    private void addLineToCanvas(Point endLine) {
        Point startLine = getStartLinePoint();
        Line line = new Line(startLine, endLine);
        line.drawLine();
        lines.add(line);
    }

    private Point getStartLinePoint() {
        return circles.get(circles.size() - 2).getCenter();
    }

    @FXML
    public void addNewPoint() {
        String pointX = newPointX.getText().trim();
        String pointY = newPointY.getText().trim();
        if (!pointX.isEmpty() && !pointY.isEmpty()) {
            double x = Double.parseDouble(pointX);
            double y = Double.parseDouble(pointY);
            addPointWithLineToCanvas(x, y);
        }
    }

    @FXML
    public void getPointToModify() {
        String pointSequence = pointPosition.getText().trim();
        Circle circle = circles.get(Integer.parseInt(pointSequence) - 1);

        Point center = circle.getCenter();
        modifyPointX.setText(String.valueOf((int) center.getX()));
        modifyPointY.setText(String.valueOf((int) center.getY()));

        shapeSelector.setCircle(circle);
        shapeSelector.setLines(lines, center.getX(), center.getY());
    }

    @FXML
    public void modifyPoint() {
        redrawPoint(Double.parseDouble(modifyPointX.getText()), Double.parseDouble(modifyPointY.getText()));
        drawBezier();
        shapeSelector.setCircleToEmpty();
        shapeSelector.setLinesToEmpty();
        clearInputs();
    }

    private void clearInputs() {
        modifyPointX.clear();
        modifyPointY.clear();
        pointPosition.clear();
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
