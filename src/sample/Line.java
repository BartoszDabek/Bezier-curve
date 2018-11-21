package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class Line {

    private Point start;
    private Point end;
    private Canvas canvas;

    Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    void drawLine() {
        canvas = new Canvas();
        canvas.setHeight(600);
        canvas.setWidth(1215);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    Canvas getCanvas() {
        return canvas;
    }
}
