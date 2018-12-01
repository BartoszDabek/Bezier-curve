package sample.shape;

import javafx.scene.canvas.GraphicsContext;
import sample.Controller;

public class Circle extends AbstractShape {

    private static final int CIRCLE_RADIUS = 5;
    private Point center;
    private double radius;
    private int sequence;

    public Circle(Point center, int sequence) {
        this(center, CIRCLE_RADIUS, sequence);
    }

    public Circle(Point center, double radius, int sequence) {
        this.center = center;
        this.radius = radius;
        this.sequence = sequence;
    }

    public void drawCircle() {
        GraphicsContext gc = getCanvas().getGraphicsContext2D();
        gc.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        gc.strokeText(String.valueOf(sequence), center.getX() - CIRCLE_RADIUS, center.getY() - 10);
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
