package at.fh.ooe.gp2.template;

import at.fh.ooe.gp2.template.api.Coordinate;
import at.fh.ooe.gp2.template.api.generator.Generator;
import at.fh.ooe.gp2.template.api.shape.*;
import at.fh.ooe.gp2.template.impl.FreemarkerGenerators;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Main {

    public static Diagram createDiagramBoxes() throws Exception {
        final Color stroke = Color.red;
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramFreemarkerGenerator(), 500, 400, false);
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(0, 0), stroke, Color.black, 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(0, 100), stroke, Color.decode("#444444"), 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(0, 200), stroke, Color.decode("#888888"), 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(0, 300), stroke, Color.decode("#cccccc"), 100, 500, 1));

        return diagram;
    }


    public static Diagram createDiagramPoint() throws Exception {
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramFreemarkerGenerator(), 500, 400, -3.0, 3.0, -3.0, 3.0, true);
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-2.0, 2.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-1.0, -1.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-0.5, -0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-0.25, 0.25), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(0.0, -1.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(0.0, 1.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(0.25, -0.25), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(0.5, 0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(1.0, 1.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(2.0, -2.0), null, Color.black, 0.05, 0.01));

        return diagram;
    }

    public static Diagram createDiagramRectangular() throws Exception {
        // left positive quadrant
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramFreemarkerGenerator(), 500, 400, -3.0, 3.0, -3.0, 3.0, true);
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(-2.8, -3), Color.red, Color.black, 3, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(-2, -1.5), Color.green, Color.yellow, 1.5, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(-1, -1.2), Color.black, Color.black, 1.2, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, new FreemarkerGenerators.RectangularFreemarkerGenerator(), new Coordinate(-0.5, -0.8), Color.blue, Color.black, 0.8, 0.1,0.01));

        // left negative quadrant
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-0.5, 0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-1.0, 1.0), null, Color.red, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-2.0, 1.0), null, Color.green, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-2.5, 2.8), null, Color.yellow, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, new FreemarkerGenerators.PointFreemarkerGenerator(), new Coordinate(-3.0, 3.0), null, Color.black, 0.05, 0.01));

        // right positive quadrant
        diagram.addShape(new TextShape(diagram, new FreemarkerGenerators.TextFreemarkerGenerator(), new Coordinate(0,-2), Color.black, Color.lightGray, "My Text", "Verdana", 0.5, 0.01));
        diagram.addShape(new TextShape(diagram, new FreemarkerGenerators.TextFreemarkerGenerator(), new Coordinate(0,-1), Color.black, Color.lightGray, "is here", "Verdana", 0.5, 0.01));

        // right negative quadrant
        diagram.addShape(new LineShape(diagram, new FreemarkerGenerators.LineFreemarkerGenerator(), new Coordinate(0,0), new Coordinate(2, 2), Color.BLACK, 0.1));
        diagram.addShape(new LineShape(diagram, new FreemarkerGenerators.LineFreemarkerGenerator(), new Coordinate(1,0), new Coordinate(1, 2), Color.RED, 0.05));
        return diagram;
    }

    private static final void createSvgFile(final Diagram diagram,
                                            final Generator<Diagram> generator,
                                            final String filename) throws Exception {
        String svgContent = generator.generate(diagram);
        File svgFile = new File("output/" + filename + ".svg");
        Writer fileWriter = new FileWriter(svgFile);
        fileWriter.write(svgContent);
        fileWriter.close();

        System.out.println("svg generated to " + svgFile.getAbsolutePath());
    }

    public static void main(String[] args) throws Exception {
        final Generator<Diagram> generator = new FreemarkerGenerators.DiagramFreemarkerGenerator();

        createSvgFile(createDiagramBoxes(), generator, "boxes");
        createSvgFile(createDiagramPoint(), generator, "points");
        createSvgFile(createDiagramRectangular(), generator, "all");
    }
}
