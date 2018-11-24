package sample.shape.bezier;

import sample.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BezierChain {

    private List<BezierCurve> bezierCurves;
    private List<Circle> tempCircles;

    public BezierChain() {
        bezierCurves = new ArrayList<>();
        tempCircles = new ArrayList<>();
    }

    public void createCurveIfPossible(Circle circle) {
        tempCircles.add(circle);
        if (tempCircles.size() == 3) {
            createQuadraticCurve();
        } else if (tempCircles.size() > 3) {
            deleteQuadraticCurve();
            createCubicCurve();
            resetTempCircles();
        }
    }

    private void createQuadraticCurve() {
        BezierCurve quadraticBezier = new QuadraticBezier(tempCircles);
        bezierCurves.add(quadraticBezier);
        quadraticBezier.draw();
    }

    private void deleteQuadraticCurve() {
        BezierCurve quadraticBezier = bezierCurves.get(bezierCurves.size() - 1);
        quadraticBezier.remove();
        bezierCurves.remove(quadraticBezier);
    }

    private void createCubicCurve() {
        BezierCurve cubicBezier = new CubicBezier(tempCircles);
        bezierCurves.add(cubicBezier);
        cubicBezier.draw();
    }

    private void resetTempCircles() {
        Circle circle = tempCircles.get(3);
        tempCircles = new ArrayList<>();
        tempCircles.add(circle);
    }

    public List<BezierCurve> getBezierCurves() {
        return bezierCurves;
    }
}
