package aspects.util;

import at.fh.ooe.gp2.template.api.shape.Diagram;
import at.fh.ooe.gp2.template.impl.generator.FreemarkerGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

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
    }

    private int height;
    private int width;
    private List<YValue> yValues;

    private static final Logger log = LoggerFactory.getLogger(ReportContext.class);

    public ReportContext(int height,
                         int width) {
        this.height = height;
        this.width = width;

        reset();
    }

    public void reset() {
        yValues = new LinkedList<>();
    }

    public void add(final double best,
                    final double worst,
                    final double average) {
        yValues.add(new YValue(best, worst, average));
    }

    public void reportToConsole() {
        int run = 0;
        for (final YValue item : yValues) {
            log.info("run={}: best={} / worst={} / average={}", run, item.best, item.worst, item.average);
            run++;
        }
    }

    public void reportToSvg() {
        try {
            final Diagram diagram = new Diagram(new FreemarkerGenerators.DiagramGenerator(), width, height, false);
            for (final YValue value : yValues) {
                final double normalizedYBest = 0.0;
                final double normalizedYWorst = 0.0;
                final double normalizedYAverage = 0.0;
            }
        } catch (Exception e) {
            log.error("Svg report could not be generated", e);
        }
    }
}
