package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class Circle {

    private Point center;
    private double radius;
    private Canvas canvas;

    Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    void drawCircle() {
        canvas = new Canvas();
        canvas.setHeight(600);
        canvas.setWidth(1215);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    Point getCenter() {
        return center;
    }

    Canvas getCanvas() {
        return canvas;
    }
}
