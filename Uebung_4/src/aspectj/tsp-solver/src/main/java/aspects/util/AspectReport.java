package aspects.util;

import at.fh.ooe.gp2.template.api.Coordinate;
import at.fh.ooe.gp2.template.api.shape.Diagram;
import at.fh.ooe.gp2.template.api.shape.LineShape;
import at.fh.ooe.gp2.template.api.shape.PointShape;
import at.fh.ooe.gp2.template.api.shape.TextShape;
import at.fh.ooe.gp2.template.impl.generator.FreemarkerGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents a report context for the evaluated solutions evaluated during an algorithm execution.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/14/17
 */
public class AspectReport {

    /**
     * Represents a y value of a run report
     */
    private static final class YValue {
        public final double best;
        public final double worst;
        public final double average;

        public YValue(double best,
                      double worst,
                      double average) {
            this.best = best;
            this.worst = worst;
            this.average = average;
        }

        public double getBest() {
            return best;
        }

        public double getWorst() {
            return worst;
        }

        public double getAverage() {
            return average;
        }
    }

    private final int height;
    private final int width;
    private final double stokeWidth;
    private final double pathStokeWidth;
    private final Color bestStrokeColor;
    private final Color worstStrokeColor;
    private final Color avgStrokeColor;
    private final String chartFilename;
    private final String pathFilename;
    private final List<YValue> yRunValues;
    private final List<Coordinate> pathValues;

    private static final Logger log = LoggerFactory.getLogger(AspectjConfig.LOGGER_NAME);
    private static final double DEFAULT_STROKE_WIDTH = 0.8;
    private static final double DEFAULT_PATH_STROKE_WIDTH = 0.5;
    private static final Color DEFAULT_BEST_COLOR = Color.GREEN;
    private static final Color DEFAULT_WORST_COLOR = Color.RED;
    private static final Color DEFAULT_AVG_COLOR = Color.ORANGE;

    /**
     * @param chartFilename the filename of the run report
     * @param pathFilename  the filename of the path report
     */
    public AspectReport(final String chartFilename,
                        final String pathFilename) {
        this.height = 700;
        this.width = 900;
        stokeWidth = DEFAULT_STROKE_WIDTH;
        bestStrokeColor = DEFAULT_BEST_COLOR;
        worstStrokeColor = DEFAULT_WORST_COLOR;
        avgStrokeColor = DEFAULT_AVG_COLOR;
        pathStokeWidth = DEFAULT_PATH_STROKE_WIDTH;
        this.chartFilename = chartFilename;
        this.pathFilename = pathFilename;

        pathValues = new LinkedList<>();
        yRunValues = new LinkedList<>();
    }

    /**
     * Adds a run y value
     *
     * @param best    the best of the run
     * @param worst   the worst of the run
     * @param average the average of the run
     */
    public void addRunValue(final double best,
                            final double worst,
                            final double average) {
        yRunValues.add(new YValue(best, worst, average));
    }

    /**
     * Adds a path coordinate.
     *
     * @param coordinate the coordinate of the path
     */
    public void addPathValue(final Coordinate coordinate) {
        pathValues.add(coordinate);
    }

    /**
     * Generates all supported reports.
     */
    public void generateAllReports() {
        generateConsoleReport();
        generateRunSvgReport();
        generatePathSvgReport();
    }

    /**
     * Generates the console run report.
     */
    public void generateConsoleReport() {
        int run = 0;
        for (final YValue item : yRunValues) {
            log.info("run={}: best={} / worst={} / average={}", run, item.best, item.worst, item.average);
            run++;
        }
    }

    /**
     * Generates the run svg report.
     */
    public void generateRunSvgReport() {
        if(yRunValues.isEmpty()) {
            log.warn("Cannot create run report because no run data available");
            return;
        }
        try {
            // get max value for normalization over all values of all captured types
            final Set<Double> allValues = new HashSet<Double>() {{
                addAll(yRunValues.stream().map(YValue::getBest).collect(Collectors.toList()));
                addAll(yRunValues.stream().map(YValue::getAverage).collect(Collectors.toList()));
                addAll(yRunValues.stream().map(YValue::getWorst).collect(Collectors.toList()));
            }};

            // Lower bound is 0.0 if value not smaller than 0
            double minValue = allValues.stream().min(Double::compare).orElse(0.0);
            minValue = (minValue >= 0) ? 0.0 : minValue;

            // get maximum value which will be the upper bound
            double maxValue = allValues.stream().max(Double::compare).orElse(0.0);

            // freemarker generators
            final FreemarkerGenerators.DiagramGenerator diagramGenerator = new FreemarkerGenerators.DiagramGenerator();
            final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
            final Diagram diagram = new Diagram(diagramGenerator, width, height, 0.0, (double) width, 0.0, (double) height, false);

            // chart margins and dimensions
            final double widthMargin = 50.0;
            final double heightMargin = 25.0;
            final double chartWidth = width - (widthMargin * 2);
            final double chartHeight = height - (heightMargin * 2);
            final double chartStep = chartWidth / yRunValues.size();

            generatePlotAxis(diagram, 0.0, yRunValues.size(), minValue, maxValue, widthMargin, heightMargin);

            double currentStep = 0.0 + widthMargin;
            // remember origin for next value
            Coordinate origBest, origWorst, origAvg;
            origBest = origWorst = origAvg = null;
            for (final YValue value : yRunValues) {
                final double yBest = normalizeValue(minValue, maxValue, 0.0, chartHeight, value.best);
                final double yWorst = normalizeValue(minValue, maxValue, 0.0, chartHeight, value.worst);
                final double yAvg = normalizeValue(minValue, maxValue, 0.0, chartHeight, value.average);

                // coordinates with inverted y position
                final Coordinate destBest = new Coordinate(currentStep, (chartHeight - yBest + heightMargin));
                final Coordinate destWorst = new Coordinate(currentStep, (chartHeight - yWorst + heightMargin));
                final Coordinate destAvg = new Coordinate(currentStep, (chartHeight - yAvg + heightMargin));

                diagram.addShape(new LineShape(diagram, lineGenerator, (origBest != null) ? origBest : destBest, destBest, bestStrokeColor, stokeWidth));
                diagram.addShape(new LineShape(diagram, lineGenerator, (origWorst != null) ? origWorst : destWorst, destWorst, worstStrokeColor, stokeWidth));
                diagram.addShape(new LineShape(diagram, lineGenerator, (origAvg != null) ? origAvg : destAvg, destAvg, avgStrokeColor, stokeWidth));

                origBest = destBest;
                origWorst = destWorst;
                origAvg = destAvg;
                currentStep += chartStep;
            }

            // Generate svg files
            generateFile(chartFilename, diagram, diagramGenerator);
        } catch (Throwable e) {
            log.error("Svg report could not be generated", e);
        }
    }


    /**
     * Generates the path svg report.
     */
    public void generatePathSvgReport() {
        if(pathValues.isEmpty()) {
            log.warn("Cannot create path report because no path data available");
            return;
        }
        try {
            double minXValue = pathValues.stream().map(Coordinate::getX).min(Double::compare).orElse(0.0);
            double maxXValue = pathValues.stream().map(Coordinate::getX).max(Double::compare).orElse(0.0);
            double minYValue = pathValues.stream().map(Coordinate::getY).min(Double::compare).orElse(0.0);
            double maxYValue = pathValues.stream().map(Coordinate::getY).max(Double::compare).orElse(0.0);

            // freemarker generators
            final FreemarkerGenerators.DiagramGenerator diagramGenerator = new FreemarkerGenerators.DiagramGenerator();
            final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
            final FreemarkerGenerators.TextGenerator textGenerator = new FreemarkerGenerators.TextGenerator();
            final FreemarkerGenerators.PointGenerator pointGenerator = new FreemarkerGenerators.PointGenerator();
            final Diagram diagram = new Diagram(diagramGenerator, width, height, 0.0, (double) width, 0.0, (double) height, false);

            // chart margins and dimensions
            final double widthMargin = 50.0;
            final double heightMargin = 25.0;
            final double chartWidth = width - (widthMargin * 2);
            final double chartHeight = height - (heightMargin * 2);

            generatePlotAxis(diagram, minXValue, maxXValue, minYValue, maxYValue, widthMargin, heightMargin);

            // remember origin for next value
            Coordinate origBest = null;
            int counter = 1;
            for (final Coordinate coordinate : pathValues) {
                final double x = normalizeValue(minXValue, maxXValue, 0.0, chartWidth, coordinate.getX());
                final double y = normalizeValue(minYValue, maxYValue, 0.0, chartHeight, coordinate.getY());

                // coordinates with inverted y position
                final Coordinate destBest = new Coordinate(x + widthMargin, (chartHeight - y + heightMargin));
                diagram.addShape(new LineShape(diagram, lineGenerator, (origBest != null) ? origBest : destBest, destBest, bestStrokeColor, pathStokeWidth));
                double radius = 1.0;
                if ((counter == 1) || (counter == pathValues.size())) {
                    radius = 2.5;
                }
                diagram.addShape(new PointShape(diagram, pointGenerator, destBest, Color.RED, Color.RED, radius, 1.0));
                if ((counter == 1) || (counter == pathValues.size())) {
                    diagram.addShape(new TextShape(diagram, textGenerator, destBest, Color.BLACK, null, String.valueOf(counter), "Arial", 9.0, 0.5));
                }

                origBest = destBest;
                counter++;
            }
            generateFile(pathFilename, diagram, diagramGenerator);
        } catch (Throwable e) {
            log.error("Svg report could not be generated", e);
        }
    }

    /**
     * Generates the plot axis with markers.
     *
     * @param diagram      the diagram to add plot to
     * @param minX         the minimum x value for axis markers
     * @param maxX         the maximum x value for axis markers
     * @param minY         the minimum y value for axis markers
     * @param maxY         the maximum y value for axis markers
     * @param widthMargin  the margin to the left and right
     * @param heightMargin the margin to the top and bottom
     * @throws Exception if the generation fails for any reason
     */
    private void generatePlotAxis(final Diagram diagram,
                                  final double minX,
                                  final double maxX,
                                  final double minY,
                                  final double maxY,
                                  final double widthMargin,
                                  final double heightMargin) throws Exception {
        // freemarker generators
        final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
        final FreemarkerGenerators.TextGenerator textGenerator = new FreemarkerGenerators.TextGenerator();

        // chart dimensions
        final double chartWidth = width - (widthMargin * 2);
        final double chartHeight = height - (heightMargin * 2);

        // Coordinate lines
        final LineShape xAxis = new LineShape(diagram, lineGenerator, new Coordinate(0.0, (height - heightMargin)), new Coordinate(width - widthMargin, (height - heightMargin)), Color.BLACK, 1.0);
        final LineShape yAxis = new LineShape(diagram, lineGenerator, new Coordinate(widthMargin, height), new Coordinate(widthMargin, heightMargin), Color.BLACK, 1.0);
        diagram.addShape(xAxis);
        diagram.addShape(yAxis);

        // Add xAxis markers
        final int markerStep = 25;
        for (int i = 1; i <= markerStep; i++) {
            // x, y position for x,y marker
            final double xPos = widthMargin + ((chartWidth / markerStep) * i);
            final double yPos = (height - heightMargin) - ((chartHeight / markerStep) * i);

            // Calculate marker values
            final String yMarkerValue = (i == markerStep)
                    ? BigDecimal.valueOf(maxY).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()
                    : BigDecimal.valueOf(maxY - minY)
                                .setScale(2, BigDecimal.ROUND_DOWN)
                                .divide(BigDecimal.valueOf(markerStep), BigDecimal.ROUND_HALF_EVEN)
                                .multiply(BigDecimal.valueOf(i))
                                .toString();
            final String xMarkerValue = (i == markerStep)
                    ? BigDecimal.valueOf(maxX).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()
                    : BigDecimal.valueOf(maxX - minX)
                                .setScale(2, BigDecimal.ROUND_DOWN)
                                .divide(BigDecimal.valueOf(markerStep), BigDecimal.ROUND_HALF_EVEN)
                                .multiply(BigDecimal.valueOf(i))
                                .toString();

            // Add axis markers
            diagram.addShape(new LineShape(diagram,
                                           lineGenerator,
                                           new Coordinate(widthMargin - 5, yPos),
                                           new Coordinate(widthMargin + 5, yPos),
                                           Color.BLACK,
                                           1.0));
            diagram.addShape(new LineShape(diagram,
                                           lineGenerator,
                                           new Coordinate(xPos, (height - heightMargin - 5)),
                                           new Coordinate(xPos, (height - heightMargin + 5)),
                                           Color.BLACK,
                                           1.0));
            diagram.addShape(new LineShape(diagram,
                                           lineGenerator,
                                           new Coordinate(widthMargin, yPos),
                                           new Coordinate(width - widthMargin, yPos),
                                           Color.DARK_GRAY,
                                           0.1));

            // Adda xis marker texts
            final int markerValueYOffset = ((i % 2) != 0) ? 0 : 9;
            diagram.addShape(new TextShape(diagram,
                                           textGenerator,
                                           new Coordinate(xPos - 15, height - heightMargin + 15 + markerValueYOffset),
                                           Color.BLACK,
                                           null,
                                           xMarkerValue,
                                           "Arial",
                                           8.5,
                                           0.25));
            diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(5, yPos - 5), Color.BLACK, null, yMarkerValue, "Arial", 8.5, 0.25));
        }
    }

    /**
     * Generate the svg diagram file.
     *
     * @param filename         the filename of the svg diagram
     * @param diagram          the diagram to be saved
     * @param diagramGenerator the generator for the diagram
     * @throws Exception if the generation fails for any reason
     */
    private void generateFile(final String filename,
                              final Diagram diagram,
                              final FreemarkerGenerators.DiagramGenerator diagramGenerator) throws Exception {
        // Generate svg files
        String svgContent = diagramGenerator.generate(diagram);
        File svgFile = File.createTempFile(filename, ".svg");
        try (final Writer fileWriter = new FileWriter(svgFile)) {
            fileWriter.write(svgContent);
        }
        log.info("SVG file location: {}", svgFile.getAbsolutePath());
    }

    /**
     * Normalizes the values in the new range
     *
     * @param oldMin the old minimum
     * @param oldMax the old maximum
     * @param newMin the new minimum
     * @param newMax the new maximum
     * @param value  the value to normalize
     * @return the normalized value
     */
    private double normalizeValue(final double oldMin,
                                  final double oldMax,
                                  final double newMin,
                                  final double newMax,
                                  final double value) {
        return (((newMin + (value - oldMin)) * (newMax - newMin)) / (oldMax - oldMin));
    }
}
