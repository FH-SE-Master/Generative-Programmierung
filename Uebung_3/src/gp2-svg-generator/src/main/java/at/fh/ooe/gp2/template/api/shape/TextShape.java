package at.fh.ooe.gp2.template.api.shape;

import at.fh.ooe.gp2.template.impl.FreemarkerGenerators;
import at.fh.ooe.gp2.template.api.Coordinate;
import lombok.Getter;

import java.awt.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class TextShape extends AbstractShape<TextShape> {

    @Getter
    private final String text;
    @Getter
    private final String fontFamily;
    @Getter
    private final double fontSize;

    public TextShape(final Shape diagram,
                     final FreemarkerGenerators.TextFreemarkerGenerator generator,
                     final Coordinate coordinate,
                     final String text) {
        this(diagram, generator, coordinate, null, null, text, "Verdana", 10, 1);
    }

    public TextShape(final Shape diagram,
                     final FreemarkerGenerators.TextFreemarkerGenerator generator,
                     final Coordinate origin,
                     final Color stroke,
                     final Color fill,
                     final String text,
                     final String fontFamily,
                     final double fontSize,
                     final double strokeWidth) {
        super(diagram, generator, origin, stroke, fill, strokeWidth);
        this.text = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
