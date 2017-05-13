package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This aspect counts the solution evaluation within the {@link tsp.GA} class.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/13/17
 */
public abstract aspect CountEvaluatedSolutionsAspect {

    long solutionCount = 0;

    private static final Logger log = LoggerFactory.getLogger(CountEvaluatedSolutionsAspect.class);

    before(): call(*.*.Solution *.GA.execute(..))
            && !within(*.GA){
        solutionCount = 0;
    }

    after(): call(* *.*.Solution.evaluate(..))
            && within(*.GA) {
        solutionCount++;
    }

    after(): call(* *.*.Solution.evaluate(..))
            && within(*.GA) {
        solutionCount++;
    }

    after(): call(*.*.Solution *.GA.execute(..))
            && !within(*.GA) {
        log.info("Evaluation count: '{}'", solutionCount);
        solutionCount = 0;
    }
}
