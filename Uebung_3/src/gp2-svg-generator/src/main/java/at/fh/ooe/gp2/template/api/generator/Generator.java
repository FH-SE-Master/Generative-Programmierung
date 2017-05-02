package at.fh.ooe.gp2.template.api.generator;

import at.fh.ooe.gp2.template.api.exception.GeneratorException;
import at.fh.ooe.gp2.template.api.shape.Shape;

/**
 * This interface marks a class as a generator.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public interface Generator<S extends Shape> {

    /**
     * Generates the specific representation of the shapes.
     *
     * @param shape the shape to generate representation for
     * @return the specific string representation of the shape
     * @throws GeneratorException if the generation fails
     */
    String generate(S shape) throws GeneratorException;
}
