package gp2.templating.shape.api;

import gp2.templating.generator.api.Generator;
import gp2.templating.generator.exception.GeneratorException;
import gp2.templating.generator.exception.RenderException;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public abstract class AbstractShape implements Shape {

    @Getter
    private final Shape parent;
    @Getter
    private final Generator generator;
    @Getter
    private final Coordinate origin;
    private final Color strokeColor;
    private final Color fillColor;

    public AbstractShape(final Generator generator,
                         final Coordinate origin) {
        this(null, generator, origin, null, null);
    }

    public AbstractShape(final Shape parent,
                         final Generator generator,
                         final Coordinate origin) {
        this(parent, generator, origin, null, null);
    }

    public AbstractShape(final Shape parent,
                         final Generator generator,
                         final Coordinate origin,
                         final Color strokeColor,
                         final Color fillColor) {
        Objects.requireNonNull(generator, "Shape must hold an generator");

        this.parent = parent;
        this.generator = generator;
        this.origin = origin;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    public String render() throws RenderException {
        try {
            return generator.generate(this);
        } catch (GeneratorException e) {
            throw new RenderException("Shape execution failed", e);
        }
    }

    protected String colorToHexString(final Color color) {
        String hex = null;
        if (color != null) {
            hex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
        }
        return hex;
    }

    public String getStrokeColor() {
        return (strokeColor != null) ? colorToHexString(strokeColor) : null;
    }

    public String getFillColor() {
        return (fillColor != null) ? colorToHexString(fillColor) : null;
    }
}
