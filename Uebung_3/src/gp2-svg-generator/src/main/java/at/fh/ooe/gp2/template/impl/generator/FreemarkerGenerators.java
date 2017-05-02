package at.fh.ooe.gp2.template.impl.generator;

import at.fh.ooe.gp2.template.api.shape.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/30/17
 */
public class FreemarkerGenerators {

    private FreemarkerGenerators() {
    }

    public static class DiagramGenerator extends AbstractFreemarkerGenerator<Diagram> {

        public DiagramGenerator() throws Exception {
            super("diagram.ftl");
        }
    }

    public static class PointGenerator extends AbstractFreemarkerGenerator<PointShape> {

        public PointGenerator() throws Exception {
            super("point.ftl");
        }
    }

    public static class LineGenerator extends AbstractFreemarkerGenerator<LineShape> {

        public LineGenerator() throws Exception {
            super("line.ftl");
        }
    }

    public static class RectangularGenerator extends AbstractFreemarkerGenerator<RectangularShape> {

        public RectangularGenerator() throws Exception {
            super("rect.ftl");
        }
    }

    public static class TextGenerator extends AbstractFreemarkerGenerator<TextShape> {

        public TextGenerator() throws Exception {
            super("text.ftl");
        }
    }
}
