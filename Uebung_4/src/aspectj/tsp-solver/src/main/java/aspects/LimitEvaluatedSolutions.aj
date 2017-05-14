package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This aspects limits the evaluated solutions, where the next iteration is skipped if the former onw
 * has reached the predefined border.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/13/17
 */
public aspect LimitEvaluatedSolutions extends CountEvaluatedSolutionsAspect {

    private boolean skipped;

    private static final Logger log = LoggerFactory.getLogger(LimitEvaluatedSolutions.class);

    before(): call(*.*.Solution *.*.Algorithm.execute(..))
            && !within(*.*.Algorithm+){
        skipped = false;
    }

    void around(): if(AspectjConfig.limitIterationsActive)
            &&  call(* *.*.Algorithm.iterate(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        if (solutionCount < AspectjConfig.maxSolutions) {
            proceed();
        } else if (!skipped) {
            skipped = true;
            log.info("Iteration stopped because max evaluations have been reached. evaluations='{}'", solutionCount);
        }
    }

    boolean around(): call(boolean *.*.Algorithm.isTerminated(..))
            && withincode(* *.*.Algorithm.execute(..)) {
        return (skipped) ? skipped : proceed();
    }
}
