package sample.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BezierCurve extends AbstractShape {

    private final List<Point> points;

    public BezierCurve(ArrayList<Circle> points) {
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
        gc.setStroke(Color.ORANGE);
        gc.strokePolyline(xPoints, yPoints, iterations);
    }

    public void remove() {
        Controller.getInstance().getCanvasHolder().getChildren().remove(getCanvas());
        getCanvas().getGraphicsContext2D().clearRect(0, 0, OFFSET_WIDTH, OFFSET_HEIGHT);
    }

    private int xValueAt(double t) {
        double sum = 0;

        if (points.size() == 3) {
            for (int i = 0, n = points.size(); i < n; i++) {
                sum += points.get(i).getX() * BezierFunctions.quadratic[i].of(t);
            }
        } else if (points.size() == 4) {
            for (int i = 0, n = points.size(); i < n; i++) {
                sum += points.get(i).getX() * BezierFunctions.cubic[i].of(t);
            }
        }

        return (int)sum;
    }

    private int yValueAt(double t) {
        double sum = 0;

        if (points.size() == 3) {
            for (int i = 0, n = points.size(); i < n; i++) {
                sum += points.get(i).getY() * BezierFunctions.quadratic[i].of(t);
            }
        } else if (points.size() == 4) {
            for (int i = 0, n = points.size(); i < n; i++) {
                sum += points.get(i).getY() * BezierFunctions.cubic[i].of(t);
            }
        }

        return (int)sum;
    }
}
