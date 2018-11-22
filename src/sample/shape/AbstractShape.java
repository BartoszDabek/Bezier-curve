package sample.shape;

import javafx.scene.canvas.Canvas;

public abstract class AbstractShape {

    public static final int OFFSET_WIDTH = 1215;
    public static final int OFFSET_HEIGHT = 600;
    private Canvas canvas;

    AbstractShape() {
        this.canvas = new Canvas();
        this.canvas.setHeight(OFFSET_HEIGHT);
        this.canvas.setWidth(OFFSET_WIDTH);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
