package aspects;

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

    private static final Logger log = LoggerFactory.getLogger(CountEvaluatedSolutionsAspect.class);

    before(): if(aspects.util.AspectjConfig.countSolutionsEnabled)
            && call(*.*.Solution *.*.Algorithm.execute(..))
            && !within(*.*.Algorithm+){
        solutionCount = 0;
    }

    after(): if(aspects.util.AspectjConfig.countSolutionsEnabled)
            &&call(* *.*.Solution.evaluate(..))
            && within(*.*.Algorithm+) {
        solutionCount++;
    }

    after(): if(aspects.util.AspectjConfig.countSolutionsEnabled)
            && call(*.*.Solution *.*.Algorithm.execute(..))
            && !within(*.*.Algorithm+) {
        log.info("Evaluation count: '{}'", solutionCount);
        solutionCount = 0;
    }
}
