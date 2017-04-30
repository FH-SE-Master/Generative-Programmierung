package gp2.templating.shape.impl.svg;


import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.AbstractShape;
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
    private final double height;
    @Getter
    private final double width;

    public RectangularShape(final Shape diagram,
                            final Generator generator,
                            final Coordinate origin,
                            final Color stroke,
                            final Color fill,
                            double height,
                            double width) {
        super(diagram, generator, origin, stroke, fill);
        this.height = height;
        this.width = width;
    }
}
