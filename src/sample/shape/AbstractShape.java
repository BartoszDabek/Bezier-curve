package sample.shape;

import javafx.scene.canvas.Canvas;

public abstract class AbstractShape {

    protected static final int OFFSET_WIDTH = 1215;
    protected static final int OFFSET_HEIGHT = 600;
    private Canvas canvas;

    protected AbstractShape() {
        this.canvas = new Canvas();
        this.canvas.setHeight(OFFSET_HEIGHT);
        this.canvas.setWidth(OFFSET_WIDTH);
    }

    protected Canvas getCanvas() {
        return canvas;
    }
}
