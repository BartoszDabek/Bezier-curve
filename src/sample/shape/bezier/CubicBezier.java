package sample.shape.bezier;

import sample.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public final class CubicBezier extends BezierCurve {

    public CubicBezier(List<Circle> points) {
        super(points);
    }

    @Override
    public BezierCurve newCurve(int i, List<Circle> canvasCircles) {
        ArrayList<Circle> temp = new ArrayList<>();
        for (int j = (i * 3) - 3; j < ((i * 3) - 3) + 4; j++) {
            if (j < canvasCircles.size()) {
                Circle circle = canvasCircles.get(j);
                temp.add(circle);
            }
        }
        return new CubicBezier(temp);
    }

    @Override
    int xValueAt(double t) {
        double sum = 0;

        for (int i = 0, n = getPoints().size(); i < n; i++) {
            sum += getPoints().get(i).getX() * BezierFunctions.cubic[i].of(t);
        }

        return (int) sum;
    }

    @Override
    int yValueAt(double t) {
        double sum = 0;

        for (int i = 0, n = getPoints().size(); i < n; i++) {
            sum += getPoints().get(i).getY() * BezierFunctions.cubic[i].of(t);
        }

        return (int) sum;
    }
}
