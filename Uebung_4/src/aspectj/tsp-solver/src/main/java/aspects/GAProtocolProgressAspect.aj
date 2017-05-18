package aspects;

import aspects.util.AspectjConfig;
import aspects.util.AspectReport;
import tsp.GA;
import tsp.api.Solution;

/**
 * This aspect protocols the best and worst found solution during the algorithm execution.
 * This aspect is for the implemented {@link GA} algorithm.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/14/17
 */
public privileged aspect GAProtocolProgressAspect {

    private AspectReport report;

    pointcut firstExecuteCall():
            if(aspects.util.AspectjConfig.reportAlgorithmEnabled)
                    && call(* *.*.Algorithm.execute(..))
                    && !within(*.*.Algorithm+);

    // Init
    before(): firstExecuteCall() {
        report = new AspectReport(AspectjConfig.reportFileName + "-chart",
                                  AspectjConfig.reportFileName + "-path");
    }

    // Report and Cleanup
    after(): firstExecuteCall() {
        report.generateConsoleReport();
        report.generateChartSvgReport();
        report.generatePathSvgReport();
        report = null;
    }

    // First population
    after(): if(aspects.util.AspectjConfig.reportAlgorithmEnabled)
            && call(* *.*.Algorithm.initialize(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        final GA target = ((GA) thisJoinPoint.getTarget());
        handleBestAndWorstAndAverage(target.population);
    }

    // All other populations
    after(): if(aspects.util.AspectjConfig.reportAlgorithmEnabled)
            && call(* *.*.Algorithm.iterate(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        final GA target = ((GA) thisJoinPoint.getTarget());
        handleBestAndWorstAndAverage(target.population);
    }

    private void handleBestAndWorstAndAverage(final Solution[] population) {
        // calculate average of population
        double average = 0.0;
        double best = 0.0;
        double worst = 0.0;

        if (population.length > 0) {
            for (final Solution solution : population) {
                average += solution.getQuality();
            }
            average = (average / population.length);
            best = population[0].getQuality();
            worst = population[population.length - 1].getQuality();
        }

        // Set calculated run results on report context
        report.add(best, worst, average);
    }
}
