package gp2.templating.shape.api;

import gp2.templating.generator.exception.RenderException;

/**
 * This interfaces marks a class as a shape.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public interface Shape {

    /**
     * Renders this shape.
     *
     * @return the rendered string representation of this shape
     * @throws RenderException if the shape rendering failed
     */
    String render() throws RenderException;
}
