package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsp.GA;
import tsp.api.Solution;

import java.util.Arrays;

/**
 * This aspects realizes the 1-elitism mechanism by replacing the worst child with the best parent of the former run.<br>
 * This aspect is for the implemented {@link GA} algorithm.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/13/17
 */
public privileged aspect GAElitismAspect {

    private Solution bestParent;

    private static final Logger log = LoggerFactory.getLogger(GAElitismAspect.class);

    Solution[] around(): if(aspects.util.AspectjConfig.elitismEnabled)
            && call(Solution[] *.GA.createChildren(..))
            && withincode(* *.GA.iterate(..)) {
        bestParent = ((GA) thisJoinPoint.getTarget()).best;

        final Solution[] children = proceed();

        if (bestParent != null) {
            Arrays.sort(children);
            final Solution worstChild = children[children.length - 1];
            children[children.length - 1] = bestParent;
            log.info("Replaced worst child with best of former run. worstChild={} / bestParent={}", worstChild.getQuality(), bestParent.getQuality());
        }

        return children;
    }
}
