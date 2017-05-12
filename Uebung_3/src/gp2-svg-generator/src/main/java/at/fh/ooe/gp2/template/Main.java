package at.fh.ooe.gp2.template;

import at.fh.ooe.gp2.template.api.Coordinate;
import at.fh.ooe.gp2.template.api.generator.Generator;
import at.fh.ooe.gp2.template.api.shape.*;
import at.fh.ooe.gp2.template.impl.generator.FreemarkerGenerators;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Main {

    public static Diagram createDiagramBoxes() throws Exception {
        final FreemarkerGenerators.RectangularGenerator rectGenerator = new FreemarkerGenerators.RectangularGenerator();
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), 500, 400, false);

        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(0, 0), null, Color.black, 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(0, 100), null, Color.decode("#444444"), 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(0, 200), null, Color.decode("#888888"), 100, 500, 1));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(0, 300), null, Color.decode("#cccccc"), 100, 500, 1));

        return diagram;
    }


    public static Diagram createDiagramPoint() throws Exception {
        final FreemarkerGenerators.PointGenerator pointGenerator = new FreemarkerGenerators.PointGenerator();
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), 500, 400, -3.0, 3.0, -3.0, 3.0, true);

        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-2.0, 2.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-1.0, -1.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-0.5, -0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-0.25, 0.25), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(0.0, -1.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(0.0, 1.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(0.25, -0.25), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(0.5, 0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(1.0, 1.0), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(2.0, -2.0), null, Color.black, 0.05, 0.01));

        return diagram;
    }

    public static Diagram createDiagramLine() throws Exception {
        final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), 500, 400, false);

        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(10,0), new Coordinate(10, 400), Color.BLACK, 4));
        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(14,0), new Coordinate(14, 500), Color.RED, 2));
        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(0,50), new Coordinate(495, 50), Color.BLUE, 5));
        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(500,0), new Coordinate(0, 400), Color.YELLOW, 10));

        return diagram;
    }

    public static Diagram createDiagramText() throws Exception {
        final FreemarkerGenerators.TextGenerator textGenerator = new FreemarkerGenerators.TextGenerator();
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), 500, 400, false);

        diagram.addShape(new TextShape(diagram,textGenerator, new Coordinate(0,40), Color.black, Color.RED, "My Text", "Verdana", 40, 1));
        diagram.addShape(new TextShape(diagram,textGenerator, new Coordinate(100,80), Color.black, Color.BLUE, "is in", "Verdana", 40, 1));
        diagram.addShape(new TextShape(diagram,textGenerator, new Coordinate(170,120), Color.black, Color.GREEN, "the Font Verdana", "Verdana", 40, 3));

        return diagram;
    }

    public static Diagram createDiagramAll() throws Exception {
        final FreemarkerGenerators.PointGenerator pointGenerator = new FreemarkerGenerators.PointGenerator();
        final FreemarkerGenerators.RectangularGenerator rectGenerator = new FreemarkerGenerators.RectangularGenerator();
        final FreemarkerGenerators.TextGenerator textGenerator = new FreemarkerGenerators.TextGenerator();
        final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
        final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), 500, 400, -3.0, 3.0, -3.0, 3.0, true);

        // left positive quadrant
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(-2.8, -3), Color.red, Color.black, 3, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(-2, -1.5), Color.green, Color.yellow, 1.5, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(-1, -1.2), Color.black, Color.black, 1.2, 0.1, 0.01));
        diagram.addShape(new RectangularShape(diagram, rectGenerator, new Coordinate(-0.5, -0.8), Color.blue, Color.black, 0.8, 0.1,0.01));

        // left negative quadrant
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-0.5, 0.5), null, Color.black, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-1.0, 1.0), null, Color.red, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-2.0, 1.0), null, Color.green, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-2.5, 2.8), null, Color.yellow, 0.05, 0.01));
        diagram.addShape(new PointShape(diagram, pointGenerator, new Coordinate(-3.0, 3.0), null, Color.black, 0.05, 0.01));

        // right positive quadrant
        diagram.addShape(new TextShape(diagram,textGenerator, new Coordinate(0,-2), Color.black, Color.lightGray, "My Text", "Verdana", 0.5, 0.01));
        diagram.addShape(new TextShape(diagram,textGenerator, new Coordinate(0,-1), Color.black, Color.lightGray, "is here", "Verdana", 0.5, 0.01));

        // right negative quadrant
        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(0,0), new Coordinate(2, 2), Color.BLACK, 0.1));
        diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(1,0), new Coordinate(1, 2), Color.RED, 0.05));
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
        final Generator<Diagram> generator = new FreemarkerGenerators.DiagramGenerator();

        createSvgFile(createDiagramBoxes(), generator, "boxes");
        createSvgFile(createDiagramPoint(), generator, "points");
        createSvgFile(createDiagramLine(), generator, "lines");
        createSvgFile(createDiagramText(), generator, "texts");
        createSvgFile(createDiagramAll(), generator, "all");
    }
}
