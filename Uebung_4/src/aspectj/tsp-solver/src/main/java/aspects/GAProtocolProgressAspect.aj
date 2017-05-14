package aspects;

import aspects.util.ReportContext;
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

    private ReportContext reportCtx;
    private Solution best;
    private Solution worst;

    // Init
    before(): call(* *.*.Algorithm.execute(..))
            && !within(*.*.Algorithm+) {
        best = worst = null;
        reportCtx = new ReportContext(768, 1204);
    }

    // Report and Cleanup
    after(): call(* *.*.Algorithm.execute(..))
            && !within(*.*.Algorithm+) {
        reportCtx.reportToConsole();
        reportCtx.reportToSvg();

        best = worst = null;
        reportCtx = null;
    }

    // First population
    after(): call(* *.*.Algorithm.initialize(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        final GA target = ((GA) thisJoinPoint.getTarget());
        handleBestAndWorstAndAverage(target.best, target.population);
    }

    // All other populations
    after(): call(* *.*.Algorithm.iterate(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        final GA target = ((GA) thisJoinPoint.getTarget());
        handleBestAndWorstAndAverage(target.best, target.population);
    }

    private void handleBestAndWorstAndAverage(final Solution newBest,
                                              final Solution[] population) {
        // Get new best and worst of population
        best = ((best == null) || (best.compareTo(newBest) > 0)) ? newBest : best;
        final Solution newWorst = population[population.length - 1];
        worst = ((worst == null) || (worst.compareTo(newWorst) < 0)) ? newWorst : worst;

        // calculate average of population
        double average = 0.0;
        for (final Solution solution : population) {
            average += solution.getQuality();
        }
        average = (average / population.length);

        // Set calculated run results on report context
        reportCtx.add(best.getQuality(), worst.getQuality(), average);
    }
}
