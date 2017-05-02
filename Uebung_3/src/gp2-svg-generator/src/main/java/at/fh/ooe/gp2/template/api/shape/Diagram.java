package at.fh.ooe.gp2.template.api.shape;

import at.fh.ooe.gp2.template.impl.FreemarkerGenerators;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Diagram extends AbstractShape<Diagram> {

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

    public Diagram(final FreemarkerGenerators.DiagramFreemarkerGenerator generator,
                   final int width,
                   final int height,
                   final boolean showAxis) {
        this(generator, width, height, null, null, null, null, showAxis);
    }

    public Diagram(final FreemarkerGenerators.DiagramFreemarkerGenerator generator,
                   final int width,
                   final int height,
                   final Double minX,
                   final Double maxX,
                   final Double minY,
                   final Double maxY,
                   final boolean showAxis) {
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
