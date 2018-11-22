package sample.shape;

import javafx.scene.canvas.GraphicsContext;

public class Line extends AbstractShape {

    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public void drawLine() {
        GraphicsContext gc = getCanvas().getGraphicsContext2D();
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
