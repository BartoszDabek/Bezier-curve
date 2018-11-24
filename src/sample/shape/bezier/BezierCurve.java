package sample.shape.bezier;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Controller;
import sample.shape.AbstractShape;
import sample.shape.Circle;
import sample.shape.Point;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BezierCurve extends AbstractShape {

    private static final int LINE_WIDTH = 3;
    private final List<Point> points;

    BezierCurve(List<Circle> points) {
        this.points = points.stream()
                .map(Circle::getCenter)
                .collect(Collectors.toList());
    }

    public void draw() {
        int iterations = 50;
        double[] xPoints = new double[iterations];
        double[] yPoints = new double[iterations];

        double u = 0.0d;
        double m = 1 / (double) iterations;

        for (int i = 0; i < iterations; i++) {
            xPoints[i] = xValueAt(u);
            yPoints[i] = yValueAt(u);
            u += m;
        }

        GraphicsContext gc = getCanvas().getGraphicsContext2D();
        Controller.getInstance().getCanvasHolder().getChildren().add(getCanvas());
        gc.setLineWidth(LINE_WIDTH);
        gc.setStroke(Color.ORANGE);
        gc.strokePolyline(xPoints, yPoints, iterations);
    }

    public void remove() {
        Controller.getInstance().getCanvasHolder().getChildren().remove(getCanvas());
        getCanvas().getGraphicsContext2D().clearRect(0, 0, OFFSET_WIDTH, OFFSET_HEIGHT);
    }

    public abstract BezierCurve newCurve(int i, List<Circle> canvasCircles);

    abstract int xValueAt(double t);

    abstract int yValueAt(double t);

    List<Point> getPoints() {
        return points;
    }
}
