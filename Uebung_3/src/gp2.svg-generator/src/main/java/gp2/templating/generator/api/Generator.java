package gp2.templating.generator.api;

import gp2.templating.generator.exception.GeneratorException;
import gp2.templating.shape.api.Shape;

/**
 * This interface marks a class as a generator.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public interface Generator {

    /**
     * Generates the specific representation of the shapes.
     *
     * @param shape the shape to generate representation for
     * @return the specific string representation of the shape
     * @throws GeneratorException if the generation fails
     */
    String generate(Shape shape) throws GeneratorException;
}
