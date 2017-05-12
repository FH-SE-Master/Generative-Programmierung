package aspects;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/12/17
 */
public aspect RuntimeMeasureAspect {

    private static final Logger log = LoggerFactory.getLogger(RuntimeMeasureAspect.class);
    private static StopWatch watch;

    // Log the first call
    pointcut binomMethod():
            call(long application.BinomialCoefficient.calculate(..))
                    && !within(application.BinomialCoefficient);

    before(): binomMethod() {
        watch = new StopWatch();
        watch.start();
    }

    after(): binomMethod() {
        watch.stop();
        log.info("Calculation duration: millis={}", watch.getTime());
        watch = null;
    }
}
