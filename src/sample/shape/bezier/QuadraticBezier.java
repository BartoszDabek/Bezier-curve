package sample.shape.bezier;

import sample.shape.Circle;

import java.util.List;

public final class QuadraticBezier extends BezierCurve {

    public QuadraticBezier(List<Circle> points) {
        super(points);
    }

    int xValueAt(double t) {
        double sum = 0;

        for (int i = 0, n = getPoints().size(); i < n; i++) {
            sum += getPoints().get(i).getX() * BezierFunctions.quadratic[i].of(t);
        }

        return (int) sum;
    }

    int yValueAt(double t) {
        double sum = 0;

        for (int i = 0, n = getPoints().size(); i < n; i++) {
            sum += getPoints().get(i).getY() * BezierFunctions.quadratic[i].of(t);
        }

        return (int) sum;
    }
}
