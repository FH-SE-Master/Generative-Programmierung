package gp2.templating;

import gp2.templating.generator.api.Generator;
import gp2.templating.generator.impl.FreemarkerGenerator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.impl.svg.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Main {

    public static Diagram createDiagramBoxes(Generator generator) {
        final Color stroke = Color.red;
        final Diagram diagram = new Diagram(generator, 500, 400, false);
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 0), stroke, Color.decode("#000000"), 100, 500));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 100), stroke, Color.decode("#444444"), 100, 500));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 200), stroke, Color.decode("#888888"), 100, 500));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 300), stroke, Color.decode("#cccccc"), 100, 500));

        return diagram;
    }


    public static Diagram createDiagramPoint(Generator generator) {
        final Diagram diagram = new Diagram(generator, 500, 400, -3.0, 3.0, -3.0, 3.0, true);
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(-2.0, 2.0), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(-1.0, -1.0), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(-0.5, -0.5), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(-0.25, 0.25), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(0.0, -1.5), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(0.0, 1.5), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(0.25, -0.25), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(0.5, 0.5), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(1.0, 1.0), null, Color.decode("#000000"), 0.05));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(2.0, -2.0), null, Color.decode("#000000"), 0.05));

        return diagram;
    }

    private static final void createSvgFile(final Diagram diagram,
                                            final Generator generator,
                                            final String filename) throws Exception {
        String svgContent = generator.generate(diagram);
        File svgFile = new File("output/" + filename + ".svg");
        Writer fileWriter = new FileWriter(svgFile);
        fileWriter.write(svgContent);
        fileWriter.close();

        System.out.println("svg generated to " + svgFile.getAbsolutePath());
    }

    public static void main(String[] args) throws Exception {
        final Generator generator = new FreemarkerGenerator(Main.class, "/templates/svg-shapes") {{
            registerShapeTemplate(Diagram.class, "diagram.ftl");
            registerShapeTemplate(PointShape.class, "point.ftl");
            registerShapeTemplate(LineShape.class, "line.ftl");
            registerShapeTemplate(RectangularShape.class, "rect.ftl");
            registerShapeTemplate(TextShape.class, "text.ftl");
        }};

        createSvgFile(createDiagramBoxes(generator), generator, "boxes");
        createSvgFile(createDiagramPoint(generator), generator, "points");
    }
}
