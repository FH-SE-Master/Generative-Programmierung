package gp2.templating.shape.impl;

import gp2.templating.generator.api.Generator;
import gp2.templating.generator.exception.GeneratorException;
import gp2.templating.generator.exception.RenderException;
import gp2.templating.shape.api.Shape;
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

    public AbstractShape(final Shape parent,
                         final Generator generator) {
        Objects.requireNonNull(generator, "Shape must hold an generator");

        this.parent = parent;
        this.generator = generator;
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
}
