package aspects.util;

import at.fh.ooe.gp2.template.api.Coordinate;
import at.fh.ooe.gp2.template.api.shape.Diagram;
import at.fh.ooe.gp2.template.api.shape.LineShape;
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
public class ReportContext {

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
    private final Color bestStrokeColor;
    private final Color worstStrokeColor;
    private final Color avgStrokeColor;
    private final String filename;
    private List<YValue> yValues;

    private static final Logger log = LoggerFactory.getLogger(ReportContext.class);
    private static final double DEFAULT_STROKE_WIDTH = 0.8;
    private static final Color DEFAULT_BEST_COLOR = Color.GREEN;
    private static final Color DEFAULT_WORST_COLOR = Color.RED;
    private static final Color DEFAULT_AVG_COLOR = Color.ORANGE;

    public ReportContext(final String filename) {
        this.height = 700;
        this.width = 900;
        stokeWidth = DEFAULT_STROKE_WIDTH;
        bestStrokeColor = DEFAULT_BEST_COLOR;
        worstStrokeColor = DEFAULT_WORST_COLOR;
        avgStrokeColor = DEFAULT_AVG_COLOR;
        this.filename = filename;

        reset();
    }

    /**
     * Rests the report context for accepting new values
     */
    public void reset() {
        yValues = new LinkedList<>();
    }

    public void add(final double best,
                    final double worst,
                    final double average) {
        yValues.add(new YValue(best, worst, average));
    }

    /**
     * Generates the console report
     */
    public void generateConsoleReport() {
        int run = 0;
        for (final YValue item : yValues) {
            log.info("run={}: best={} / worst={} / average={}", run, item.best, item.worst, item.average);
            run++;
        }
    }

    /**
     * Generates the svg reports.
     */
    public void generateSvgReport() {
        try {
            // get max value for normalization over all values of all captured types
            final Set<Double> allValues = new HashSet<Double>() {{
                addAll(yValues.stream().map(YValue::getBest).collect(Collectors.toList()));
                addAll(yValues.stream().map(YValue::getAverage).collect(Collectors.toList()));
                addAll(yValues.stream().map(YValue::getWorst).collect(Collectors.toList()));
            }};

            // Lower bound is 0.0 if value not smaller than 0
            double minValue = allValues.stream().min(Double::compare).orElse(0.0);
            minValue = (minValue >= 0) ? 0.0 : minValue;

            // get maximum value which will be the upper bound
            double maxValue = allValues.stream().max(Double::compare).orElse(0.0);

            // freemarker generators
            final FreemarkerGenerators.DiagramGenerator diagramGenerator = new FreemarkerGenerators.DiagramGenerator();
            final FreemarkerGenerators.LineGenerator lineGenerator = new FreemarkerGenerators.LineGenerator();
            final FreemarkerGenerators.TextGenerator textGenerator = new FreemarkerGenerators.TextGenerator();
            final FreemarkerGenerators.RectangularGenerator rectGenerator = new FreemarkerGenerators.RectangularGenerator();
            final Diagram diagram = new Diagram(diagramGenerator, width, height, 0.0, (double) width, 0.0, (double) height, false);

            // chart margins and dimensions
            final double widthMargin = 50.0;
            final double heightMargin = 20.0;
            final double chartWidth = width - (widthMargin * 2);
            final double chartHeight = height - (heightMargin * 2);
            final double chartStep = chartWidth / yValues.size();

            // Coordinate lines
            final LineShape xAxis = new LineShape(diagram, lineGenerator, new Coordinate(0.0, (height - heightMargin)), new Coordinate(width - widthMargin, (height - heightMargin)), Color.BLACK, 1.0);
            final LineShape yAxis = new LineShape(diagram, lineGenerator, new Coordinate(widthMargin, height), new Coordinate(widthMargin, heightMargin), Color.BLACK, 1.0);
            diagram.addShape(xAxis);
            diagram.addShape(yAxis);

            // Value legends
            final double xLegendLinePos = width - widthMargin - 50;
            diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(xLegendLinePos, heightMargin), new Coordinate(xLegendLinePos + 10, heightMargin), bestStrokeColor, 2));
            diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(xLegendLinePos, heightMargin + 10), new Coordinate(xLegendLinePos + 10, heightMargin + 10), worstStrokeColor, 2));
            diagram.addShape(new LineShape(diagram, lineGenerator, new Coordinate(xLegendLinePos, heightMargin + 20), new Coordinate(xLegendLinePos + 10, heightMargin + 20), avgStrokeColor, 2));
            diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(xLegendLinePos + 15, heightMargin + 3), Color.BLACK, null, "Best", "Arial", 8.5, 0.25));
            diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(xLegendLinePos + 15, heightMargin + 13), Color.BLACK, null, "Worst", "Arial", 8.5, 0.25));
            diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(xLegendLinePos + 15, heightMargin + 23), Color.BLACK, null, "Average", "Arial", 8.5, 0.25));

            // Add xAxis marker
            final int markerStep = 25;
            for (int i = 1; i <= markerStep; i++) {
                // calculate x and y positions for markers
                final double xPos = widthMargin + ((chartWidth / markerStep) * i);
                final double yPos = (height - heightMargin) - ((chartHeight / markerStep) * i);

                // Calculate marker values
                final String yMarkerValue = (i == markerStep)
                        ? BigDecimal.valueOf(maxValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()
                        : BigDecimal.valueOf(maxValue)
                                    .setScale(2, BigDecimal.ROUND_DOWN)
                                    .divide(BigDecimal.valueOf(markerStep), BigDecimal.ROUND_HALF_EVEN)
                                    .multiply(BigDecimal.valueOf(i))
                                    .toString();
                final String xMarkerValue = (i == markerStep)
                        ? String.valueOf(yValues.size())
                        : String.valueOf(((yValues.size() / markerStep) * i));

                // Add axis markers
                diagram.addShape(new LineShape(diagram,
                                               lineGenerator,
                                               new Coordinate(widthMargin - 5, yPos),
                                               new Coordinate(widthMargin + 5, yPos),
                                               Color.BLACK,
                                               1.0));
                diagram.addShape(new LineShape(diagram,
                                               lineGenerator,
                                               new Coordinate(widthMargin, yPos),
                                               new Coordinate(width - widthMargin, yPos),
                                               Color.DARK_GRAY,
                                               0.1));
                diagram.addShape(new LineShape(diagram,
                                               lineGenerator,
                                               new Coordinate(xPos, (height - heightMargin - 5)),
                                               new Coordinate(xPos, (height - heightMargin + 5)),
                                               Color.BLACK,
                                               1.0));

                // Adda xis marker texts
                final int xMarkerOffset = (i < 10) ? 3 : 7;
                diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(xPos - xMarkerOffset, height - heightMargin + 15), Color.BLACK, null, xMarkerValue, "Arial", 10.0, 0.25));
                diagram.addShape(new TextShape(diagram, textGenerator, new Coordinate(5, yPos - 5), Color.BLACK, null, yMarkerValue, "Arial", 8.5, 0.25));
            }

            double currentStep = 0.0 + widthMargin;
            // remember origin for next value
            Coordinate origBest, origWorst, origAvg;
            origBest = origWorst = origAvg = null;
            for (final YValue value : yValues) {
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
            String svgContent = diagramGenerator.generate(diagram);
            File svgFile = File.createTempFile(filename, ".svg");
            try (final Writer fileWriter = new FileWriter(svgFile)) {
                fileWriter.write(svgContent);
            }
            log.info("SVG file location: {}", svgFile.getAbsolutePath());
        } catch (Throwable e) {
            log.error("Svg report could not be generated", e);
        }
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
