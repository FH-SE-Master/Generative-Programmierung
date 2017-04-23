package gp2.templating;

import gp2.templating.generators.DiagramGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Main {
    public static Diagram createDiagram() {
        Diagram diag = new Diagram(500, 500, -6, -6, 6, 2, 0.05, true);
        // add shapes ...
        return diag;
    }

    public static void main(String[] args) throws Exception {
        Diagram diag = createDiagram();
        DiagramGenerator diagGen = new DiagramGenerator();

        String svgContent = diagGen.generate(diag);
        File svgFile = new File("output/output.svg");
        Writer fileWriter = new FileWriter(svgFile);
        fileWriter.write(svgContent);
        fileWriter.close();

        System.out.println("svg generated to " + svgFile.getAbsolutePath());
    }
}
