package at.fh.ooe.gp2.template.api.shape;

import at.fh.ooe.gp2.template.api.*;
import at.fh.ooe.gp2.template.api.exception.GeneratorException;
import at.fh.ooe.gp2.template.api.exception.RenderException;
import at.fh.ooe.gp2.template.api.generator.Generator;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public abstract class AbstractShape<S extends Shape> implements Shape {

    @Getter
    private final Shape parent;
    @Getter
    private final Generator<S> generator;
    @Getter
    private final Coordinate origin;
    @Getter
    private final double strokeWidth;
    private final Color strokeColor;
    private final Color fillColor;

    public AbstractShape(final Generator<S> generator,
                         final Coordinate origin) {
        this(null, generator, origin, null, null, 1);
    }

    public AbstractShape(final at.fh.ooe.gp2.template.api.shape.Shape parent,
                         final Generator<S> generator,
                         final Coordinate origin) {
        this(parent, generator, origin, null, null, 1);
    }

    public AbstractShape(final at.fh.ooe.gp2.template.api.shape.Shape parent,
                         final Generator<S> generator,
                         final Coordinate origin,
                         final Color strokeColor,
                         final Color fillColor,
                         final double strokeWidth) {
        Objects.requireNonNull(generator, "Shape must hold an generator");

        this.parent = parent;
        this.generator = generator;
        this.origin = origin;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
    }

    public String render() throws RenderException {
        try {
            return generator.generate((S) this);
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
