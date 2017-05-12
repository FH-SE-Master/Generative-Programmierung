package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect LogRecursiveCallsAspect {

    private int callCount;

    private static final Logger log = LoggerFactory.getLogger(LogRecursiveCallsAspect.class);

    // Log the first call
    pointcut firstCall():
            if(application.Main.ActivateLogging)
                    && call(long application.BinomialCoefficient.calculate(..))
                    && !within(application.BinomialCoefficient);

    // Log all other calls
    pointcut lowerLevelLogging():
            if(application.Main.ActivateLogging)
                    && call(long application.BinomialCoefficient.calculate(..))
                    && within(application.BinomialCoefficient);

    // Init counter
    before(): firstCall() {
        callCount = 0;
    }

    // Log result and Clear counter
    after(): firstCall() {
        log.info("Recursive calls: {}", callCount);
        callCount = 0;
    }

    // Increase counter
    before(): lowerLevelLogging() {
        callCount++;
    }
}
