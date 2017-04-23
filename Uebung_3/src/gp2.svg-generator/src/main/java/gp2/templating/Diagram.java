package gp2.templating;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.impl.AbstractShape;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Diagram extends AbstractShape {

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
    private List<AbstractShape> shapes = new LinkedList<>();

    public Diagram(final Generator generator,
                   int width,
                   int height,
                   double minX,
                   double minY,
                   double maxX,
                   double maxY,
                   double defaultSize,
                   boolean showAxis) {
        super(null, generator);

        this.width = width;
        this.height = height;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.defaultSize = defaultSize;
        this.showAxis = showAxis;
    }

    public void addShape(AbstractShape s) {
        shapes.add(s);
    }
}
