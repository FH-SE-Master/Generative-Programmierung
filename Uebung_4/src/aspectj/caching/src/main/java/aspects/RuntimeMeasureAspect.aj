package aspects;

import application.Main;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/12/17
 */
public aspect RuntimeMeasureAspect extends AbstractAspect {

    private static final Logger log = LoggerFactory.getLogger(Main.LOGGER_NAME);
    private static StopWatch watch;

    @Override
    protected void beforeFirstCall() {
        if (Main.RuntimeMeasurementEnabled) {
            watch = new StopWatch();
            watch.start();
        }
    }

    @Override
    protected void afterFirstCall() {
        if (Main.RuntimeMeasurementEnabled) {
            watch.stop();
            log.info("Calculation duration: millis={}", watch.getTime());
            watch = null;
        }
    }
}
