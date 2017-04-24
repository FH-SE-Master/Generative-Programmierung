package gp2.templating.shape.impl;


import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.api.Shape;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class RectangularShape extends AbstractShape {

    @Getter
    private final Coordinate coordinate;

    @Getter
    private final double height;

    @Getter
    private final double width;

    @Getter
    private final String fill;

    public RectangularShape(Shape parent,
                            Generator generator,
                            Coordinate coordinate,
                            double height,
                            double width) {
        this(parent, generator, coordinate, height, width, null);
    }

    public RectangularShape(Shape diagram,
                            Generator generator,
                            Coordinate coordinate,
                            double height,
                            double width,
                            Color fill) {
        super(diagram, generator);
        this.coordinate = coordinate;
        this.height = height;
        this.width = width;
        this.fill = colorToHexString(fill);
    }
}
