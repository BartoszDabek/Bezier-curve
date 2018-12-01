package sample.shape.util;

import javafx.scene.input.MouseEvent;
import sample.shape.Circle;
import sample.shape.Line;

import java.util.List;
import java.util.Optional;

public class ShapeSelector {

    private static final int SELECT_RADIUS = 10;
    private Optional<Circle> circle;
    private Optional<Line> startingLine;
    private Optional<Line> endLine;

    public ShapeSelector() {
        circle = Optional.empty();
        startingLine = Optional.empty();
        endLine = Optional.empty();
    }

    public Optional<Circle> getCircle() {
        return circle;
    }

    public void setCircle(List<Circle> canvasCircles, MouseEvent event) {
        for (Circle circle : canvasCircles) {
            if (Math.abs(event.getX() - circle.getCenter().getX()) < SELECT_RADIUS &&
                    Math.abs(event.getY() - circle.getCenter().getY()) < SELECT_RADIUS) {
                this.circle = Optional.of(circle);
            }
        }
    }

    public boolean isCirclePresent() {
        return circle.isPresent();
    }

    public void setCircleToEmpty() {
        this.circle = Optional.empty();
    }

    public Optional<Line> getStartingLine() {
        return startingLine;
    }

    public Optional<Line> getEndLine() {
        return endLine;
    }

    public void setLines(List<Line> canvasLines, double x, double y) {
        for (Line line : canvasLines) {
            if (Math.abs(x - line.getStart().getX()) < SELECT_RADIUS &&
                    Math.abs(y - line.getStart().getY()) < SELECT_RADIUS) {
                this.startingLine = Optional.of(line);
            }

            if (Math.abs(x - line.getEnd().getX()) < SELECT_RADIUS &&
                    Math.abs(y - line.getEnd().getY()) < SELECT_RADIUS) {
                this.endLine = Optional.of(line);
            }
        }
    }

    public void setLinesToEmpty() {
        this.startingLine = Optional.empty();
        this.endLine = Optional.empty();
    }

    public void setCircle(Circle circle) {
        this.circle = Optional.ofNullable(circle);
    }
}
