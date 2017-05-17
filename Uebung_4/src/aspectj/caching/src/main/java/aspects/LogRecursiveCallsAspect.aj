package aspects;

import application.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect LogRecursiveCallsAspect extends AbstractAspect {

    private int callCount;

    private static final Logger log = LoggerFactory.getLogger(Main.LOGGER_NAME);

    @Override
    protected void beforeFirstCall() {
        if (Main.LoggingEnabled) {
            callCount = 0;
        }
    }

    @Override
    protected void afterFirstCall() {
        if (Main.LoggingEnabled) {
            log.info("Recursive calls: {}", callCount);
            callCount = 0;
        }
    }

    after(): if(application.Main.LoggingEnabled)
            && innerCalls() {
        callCount++;
    }
}
