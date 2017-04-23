package gp2.templating.shape.impl;

import gp2.templating.generator.api.Generator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.api.Shape;
import lombok.Getter;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class TextShape extends AbstractShape {

    @Getter
    private final Coordinate coordinate;

    @Getter
    private final String text;

    @Getter
    private final String fontFamily;

    @Getter
    private final Integer fontSize;

    public TextShape(Shape diagram,
                     Generator generator,
                     Coordinate coordinate,
                     String text) {
        this(diagram, generator, coordinate, text, "Verdana", 10);
    }

    public TextShape(Shape diagram,
                     Generator generator,
                     Coordinate coordinate,
                     String text,
                     String fontFamily,
                     Integer fontSize) {
        super(diagram, generator);
        this.coordinate = coordinate;
        this.text = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
