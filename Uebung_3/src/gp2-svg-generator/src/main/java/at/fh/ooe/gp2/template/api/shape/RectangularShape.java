package at.fh.ooe.gp2.template.api.shape;


import at.fh.ooe.gp2.template.api.*;
import at.fh.ooe.gp2.template.impl.generator.FreemarkerGenerators;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class RectangularShape extends AbstractShape<RectangularShape> {

    @Getter
    private final double height;
    @Getter
    private final double width;

    public RectangularShape(final Shape diagram,
                            final FreemarkerGenerators.RectangularGenerator generator,
                            final Coordinate origin,
                            final Color stroke,
                            final Color fill,
                            final double height,
                            final double width,
                            final double strokeWidth) {
        super(diagram, generator, origin, stroke, fill,strokeWidth);
        this.height = height;
        this.width = width;
    }
}
