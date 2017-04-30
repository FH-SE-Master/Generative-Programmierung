package gp2.templating.generator.impl;

import gp2.templating.generator.api.Generator;
import gp2.templating.generator.exception.GeneratorException;
import gp2.templating.shape.api.Shape;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/30/17
 */
public class Generators {

    private Generators() {
    }

    public static class PointGenerator implements Generator {


        @Override
        public String generate(Shape shape) throws GeneratorException {
            return null;
        }
    }
}
