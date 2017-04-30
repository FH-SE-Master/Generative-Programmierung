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
public class LineShape extends AbstractShape {

    @Getter
    private final Coordinate end;
    @Getter
    private final int strokeWidth;

    public LineShape(final Shape parent,
                     final Generator generator,
                     final Coordinate origin,
                     final Coordinate end) {
        this(parent, generator, origin, null, null, end, 1);
    }

    public LineShape(final Shape diagram,
                     final Generator generator,
                     final Coordinate origin,
                     final Color stroke,
                     final Color fill,
                     final Coordinate end,
                     int strokeWidth) {
        super(diagram, generator, origin, stroke, fill);
        this.end = end;
        this.strokeWidth = strokeWidth;
    }

}
