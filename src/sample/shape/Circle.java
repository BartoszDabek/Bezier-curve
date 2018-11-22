package sample.shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends AbstractShape {

    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public void drawCircle() {
        GraphicsContext gc = getCanvas().getGraphicsContext2D();
        gc.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }
}
