package gp2.templating.shape.impl;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.api.Shape;
import lombok.Getter;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public class PointShape extends AbstractShape {

    @Getter
    private final Coordinate coordinate;
    @Getter
    private final double r;

    public PointShape(final Shape diagram,
                      final Generator generator,
                      final Coordinate coordinate,
                      final double r) {

        super(diagram, generator);
        this.coordinate = coordinate;
        this.r = r;
    }
}
