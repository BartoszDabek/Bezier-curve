package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Circle {

    private Point center;
    private double radius;

    Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    void drawCircle(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }
}
