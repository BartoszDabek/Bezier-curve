package sample;

import sample.shape.Circle;
import sample.shape.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class BezierCurve {

    private final List<Point> points;

    BezierCurve(ArrayList<Circle> points) {
        this.points = points.stream()
                .map(Circle::getCenter)
                .collect(Collectors.toList());
    }

    int xValueAt(double t) {
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

    int yValueAt(double t) {
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
