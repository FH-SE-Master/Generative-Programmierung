package at.fh.ooe.gp2.template.api.shape;

import at.fh.ooe.gp2.template.api.*;
import at.fh.ooe.gp2.template.impl.FreemarkerGenerators;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public class PointShape extends AbstractShape<PointShape> {

    @Getter
    private final double radius;

    public PointShape(final Shape diagram,
                      final FreemarkerGenerators.PointFreemarkerGenerator generator,
                      final Coordinate origin,
                      final Color stroke,
                      final Color fill,
                      final double radius,
                      final double strokeWidth) {

        super(diagram, generator, origin, stroke, fill, strokeWidth);
        this.radius = radius;
    }
}
