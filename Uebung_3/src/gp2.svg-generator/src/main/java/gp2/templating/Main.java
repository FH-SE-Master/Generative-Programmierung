package gp2.templating;

import gp2.templating.generator.api.Generator;
import gp2.templating.generator.impl.FreemarkerGenerator;
import gp2.templating.shape.api.Coordinate;
import gp2.templating.shape.impl.LineShape;
import gp2.templating.shape.impl.PointShape;
import gp2.templating.shape.impl.RectangularShape;
import gp2.templating.shape.impl.TextShape;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Main {

    public static Diagram createDiagramBoxes(Generator generator) {
        final Diagram diagram = new Diagram(generator, 500, 400);
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 0), 100, 500, Color.decode("#000000")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 100), 100, 500, Color.decode("#444444")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 200), 200, 500, Color.decode("#888888")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 300), 300, 500, Color.decode("#cccccc")));

        return diagram;
    }

    public static Diagram createDiagramPoint(Generator generator) {
        final Diagram diagram = new Diagram(generator, 500, 400, new Coordinate(-3, -3), 6.0, 6.0, true);
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 0), 100, 500, Color.decode("#000000")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 100), 100, 500, Color.decode("#444444")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 200), 200, 500, Color.decode("#888888")));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(0, 300), 300, 500, Color.decode("#cccccc")));

        return diagram;
    }

    /*public static Diagram createDiagramBoxes(Generator generator) {
        final Diagram diagram = new Diagram(generator, 500, 500);
        diagram.addShape(new LineShape(diagram, generator, new Coordinate(0, 0), new Coordinate(100, 10)));
        diagram.addShape(new RectangularShape(diagram, generator, new Coordinate(10, 0), 50, 50));
        diagram.addShape(new PointShape(diagram, generator, new Coordinate(80, 50), 20));
        diagram.addShape(new TextShape(diagram, generator, new Coordinate(0, 150), "Hello Text"));

        return diagram;
    }*/
    public static void main(String[] args) throws Exception {
        final Generator generator = new FreemarkerGenerator(Main.class, "/templates/svg-shapes") {{
            registerShapeTemplate(Diagram.class, "diagram.ftl");
            registerShapeTemplate(PointShape.class, "point.ftl");
            registerShapeTemplate(LineShape.class, "line.ftl");
            registerShapeTemplate(RectangularShape.class, "rect.ftl");
            registerShapeTemplate(TextShape.class, "text.ftl");
        }};

        final Diagram diag = createDiagramBoxes(generator);

        String svgContent = generator.generate(diag);
        File svgFile = new File("output/output.svg");
        Writer fileWriter = new FileWriter(svgFile);
        fileWriter.write(svgContent);
        fileWriter.close();

        System.out.println("svg generated to " + svgFile.getAbsolutePath());
    }
}
