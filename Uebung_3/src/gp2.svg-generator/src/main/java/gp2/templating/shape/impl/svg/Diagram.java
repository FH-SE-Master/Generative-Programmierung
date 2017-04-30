package gp2.templating.shape.impl.svg;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.AbstractShape;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Diagram extends AbstractShape {

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final Double minX;
    @Getter
    private final Double maxX;
    @Getter
    private final Double minY;
    @Getter
    private final Double maxY;
    @Getter
    private final boolean showAxis;
    @Getter
    private final List<AbstractShape> shapes = new LinkedList<>();

    public Diagram(Generator generator,
                   int width,
                   int height,
                   boolean showAxis) {
        this(generator, width, height, null, null, null, null, showAxis);
    }

    public Diagram(Generator generator,
                   int width,
                   int height,
                   Double minX,
                   Double maxX,
                   Double minY,
                   Double maxY,
                   boolean showAxis) {
        super(generator, null);
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.showAxis = showAxis;
    }

    public void addShape(AbstractShape s) {
        shapes.add(s);
    }

    public boolean isViewBoxEnabled(){
        return (minX != null) && (maxX!= null) && (minY!=null) && (maxY!=null);
    }
}
