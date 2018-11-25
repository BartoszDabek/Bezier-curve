package sample.shape.util;

import javafx.scene.input.MouseEvent;
import sample.shape.Circle;
import sample.shape.Line;

import java.util.List;
import java.util.Optional;

public class ShapeSelector {

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
            if (Math.abs(event.getX() - circle.getCenter().getX()) < 10 &&
                    Math.abs(event.getY() - circle.getCenter().getY()) < 10) {
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

    public void setLines(List<Line> canvasLines, MouseEvent event) {
        for (Line line : canvasLines) {
            if (Math.abs(event.getX() - line.getStart().getX()) < 10 &&
                    Math.abs(event.getY() - line.getStart().getY()) < 10) {
                this.startingLine = Optional.of(line);
            }

            if (Math.abs(event.getX() - line.getEnd().getX()) < 10 &&
                    Math.abs(event.getY() - line.getEnd().getY()) < 10) {
                this.endLine = Optional.of(line);
            }
        }
    }

    public void setLinesToEmpty() {
        this.startingLine = Optional.empty();
        this.endLine = Optional.empty();
    }

}
