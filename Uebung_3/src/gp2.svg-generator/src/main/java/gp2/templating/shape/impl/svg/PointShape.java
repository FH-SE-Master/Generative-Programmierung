package gp2.templating.shape.impl.svg;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.AbstractShape;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.api.Shape;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public class PointShape extends AbstractShape {

    @Getter
    private final double radius;

    public PointShape(final Shape diagram,
                      final Generator generator,
                      final Coordinate origin,
                      final Color stroke,
                      final Color fill,
                      final double radius) {

        super(diagram, generator, origin, stroke, fill);
        this.radius = radius;
    }
}
