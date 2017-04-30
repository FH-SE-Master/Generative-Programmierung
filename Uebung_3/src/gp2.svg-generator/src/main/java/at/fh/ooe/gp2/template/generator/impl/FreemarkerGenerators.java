package at.fh.ooe.gp2.template.generator.impl;

import at.fh.ooe.gp2.template.api.shape.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/30/17
 */
public class FreemarkerGenerators {

    private FreemarkerGenerators() {
    }

    public static class DiagramFreemarkerGenerator extends AbstractFreemarkerGenerator<Diagram> {

        public DiagramFreemarkerGenerator() throws Exception {
            super("diagram.ftl");
        }
    }

    public static class PointFreemarkerGenerator extends AbstractFreemarkerGenerator<PointShape> {

        public PointFreemarkerGenerator() throws Exception {
            super("point.ftl");
        }
    }

    public static class LineFreemarkerGenerator extends AbstractFreemarkerGenerator<LineShape> {

        public LineFreemarkerGenerator() throws Exception {
            super("line.ftl");
        }
    }

    public static class RectangularFreemarkerGenerator extends AbstractFreemarkerGenerator<RectangularShape> {

        public RectangularFreemarkerGenerator() throws Exception {
            super("rect.ftl");
        }
    }

    public static class TextFreemarkerGenerator extends AbstractFreemarkerGenerator<TextShape> {

        public TextFreemarkerGenerator() throws Exception {
            super("text.ftl");
        }
    }
}
