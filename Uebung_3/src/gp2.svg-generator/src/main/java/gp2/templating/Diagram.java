package gp2.templating;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.api.Shape;
import gp2.templating.shape.impl.AbstractShape;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Diagram extends AbstractShape {

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final Coordinate coordinate;
    @Getter
    private final double coordinateHeight;
    @Getter
    private final double coordinateWidth;
    @Getter
    private final boolean showAxis;
    @Getter
    private final List<AbstractShape> shapes = new LinkedList<>();

    public Diagram(Generator generator,
                   int width,
                   int height) {
        this(generator, width, height, null, 0.0, 0.0, false);
    }

    public Diagram(final Generator generator,
                   int width,
                   int height,
                   Coordinate coordinate,
                   double coordinateHeight,
                   double coordinateWidth,
                   boolean showAxis) {
        super(null, generator);

        this.width = width;
        this.height = height;
        this.coordinate = coordinate;
        this.coordinateWidth = coordinateWidth;
        this.coordinateHeight = coordinateHeight;
        this.showAxis = showAxis;
    }

    public void addShape(AbstractShape s) {
        shapes.add(s);
    }
}
