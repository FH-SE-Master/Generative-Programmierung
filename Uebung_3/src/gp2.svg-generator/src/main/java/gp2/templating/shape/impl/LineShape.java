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
public class LineShape extends AbstractShape {

    @Getter
    private final Coordinate start;

    @Getter
    private final Coordinate end;

    @Getter
    private final int strokeWidth;

    @Getter
    private final String strokeColor;

    public LineShape(Shape parent,
                     Generator generator,
                     Coordinate start,
                     Coordinate end) {
        this(parent, generator, start, end, 1, Color.BLACK);
    }

    public LineShape(Shape diagram,
                     Generator generator,
                     Coordinate start,
                     Coordinate end,
                     int strokeWidth,
                     Color strokeColor) {
        super(diagram, generator);
        this.start = start;
        this.end = end;
        this.strokeWidth = strokeWidth;
        this.strokeColor = colorToHexString(strokeColor);
    }

}
