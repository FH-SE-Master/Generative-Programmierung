package aspects;

import aspects.util.AspectjConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This aspect counts the solution evaluation within the {@link tsp.api.Algorithm} implementations.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/13/17
 */
public abstract aspect CountEvaluatedSolutionsAspect {

    long solutionCount = 0;

    private static final Logger log = LoggerFactory.getLogger(AspectjConfig.LOGGER_NAME);

    pointcut executeCall():
            if(aspects.util.AspectjConfig.countSolutionsEnabled)
                    && call(* *.*.Algorithm.execute(..))
                    && !within(*.*.Algorithm+);

    before(): executeCall() {
        solutionCount = 0;
    }

    after(): executeCall() {
        log.info("Evaluation count: '{}'", solutionCount);
        solutionCount = 0;
    }

    after(): if(aspects.util.AspectjConfig.countSolutionsEnabled)
            &&call(* *.*.Solution.evaluate(..))
            && within(*.*.Algorithm+) {
        solutionCount++;
    }
}
