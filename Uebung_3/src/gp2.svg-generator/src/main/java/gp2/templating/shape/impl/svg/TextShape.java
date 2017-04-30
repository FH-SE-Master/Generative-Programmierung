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
public class TextShape extends AbstractShape {

    @Getter
    private final String text;
    @Getter
    private final String fontFamily;
    @Getter
    private final Integer fontSize;

    public TextShape(final Shape diagram,
                     final Generator generator,
                     final Coordinate coordinate,
                     final String text) {
        this(diagram, generator, coordinate, null, null, text, "Verdana", 10);
    }

    public TextShape(final Shape diagram,
                     final Generator generator,
                     final Coordinate origin,
                     final Color stroke,
                     final Color fill,
                     final String text,
                     final String fontFamily,
                     final Integer fontSize) {
        super(diagram, generator, origin, stroke, fill);
        this.text = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
