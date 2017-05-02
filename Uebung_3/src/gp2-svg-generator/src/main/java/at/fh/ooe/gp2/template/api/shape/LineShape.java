package at.fh.ooe.gp2.template.api.shape;

import at.fh.ooe.gp2.template.impl.FreemarkerGenerators;
import at.fh.ooe.gp2.template.api.Coordinate;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class LineShape extends AbstractShape<LineShape> {

    @Getter
    private final Coordinate end;

    public LineShape(final Shape parent,
                     final FreemarkerGenerators.LineFreemarkerGenerator generator,
                     final Coordinate origin,
                     final Coordinate end) {
        this(parent, generator, origin, end, null, 1);
    }

    public LineShape(final Shape diagram,
                     final FreemarkerGenerators.LineFreemarkerGenerator generator,
                     final Coordinate origin,
                     final Coordinate end,
                     final Color stroke,
                     final double strokeWidth) {
        super(diagram, generator, origin, stroke, null, strokeWidth);
        this.end = end;
    }

}
