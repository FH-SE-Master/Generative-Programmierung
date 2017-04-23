package gp2.templating;

import gp2.templating.shapes.AbstractShape;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Diagram {

    @Getter
    @Setter
    private int width;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private double minX;
    @Getter
    @Setter
    private double minY;
    @Getter
    @Setter
    private double maxX;
    @Getter
    @Setter
    private double maxY;
    @Getter
    @Setter
    private double defaultSize;
    @Getter
    @Setter
    private boolean showAxis;
    @Getter
    @Setter
    private List<AbstractShape> shapes;

    public Diagram() { this(100, 100, 0.0, 1.0, 0.0, 1.0, 1.0, true); }

    public Diagram(int width,
                   int height,
                   double minX,
                   double minY,
                   double maxX,
                   double maxY,
                   double defaultSize,
                   boolean showAxis) {
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.defaultSize = defaultSize;
        this.showAxis = showAxis;
        shapes = new ArrayList<>();
    }

    public void addShape(AbstractShape s) {
        shapes.add(s);
    }
}
