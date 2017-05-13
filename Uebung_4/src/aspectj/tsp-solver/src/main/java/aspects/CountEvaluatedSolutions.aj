package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/13/17
 */
public aspect CountEvaluatedSolutions {

    private long solutionCount = 0;

    private static final Logger log = LoggerFactory.getLogger(CountEvaluatedSolutions.class);


}
