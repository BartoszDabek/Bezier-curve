package sample.shape;

import javafx.scene.canvas.GraphicsContext;
import sample.Controller;

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
        Controller.getInstance().getCanvasHolder().getChildren().add(getCanvas());
    }

    public void redraw() {
        Controller.getInstance().getCanvasHolder().getChildren().remove(getCanvas());
        getCanvas().getGraphicsContext2D().clearRect(0, 0, AbstractShape.OFFSET_WIDTH, AbstractShape.OFFSET_HEIGHT);
        drawCircle();
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }
}
