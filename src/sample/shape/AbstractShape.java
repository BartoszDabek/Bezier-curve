package sample.shape;

import javafx.scene.canvas.Canvas;

public abstract class AbstractShape {

    static final int OFFSET_WIDTH = 1215;
    static final int OFFSET_HEIGHT = 600;
    private Canvas canvas;

    AbstractShape() {
        this.canvas = new Canvas();
        this.canvas.setHeight(OFFSET_HEIGHT);
        this.canvas.setWidth(OFFSET_WIDTH);
    }

    Canvas getCanvas() {
        return canvas;
    }
}
