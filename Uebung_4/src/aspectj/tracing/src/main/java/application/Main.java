package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for testing the implemented aspects.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class Main {

    public static final String LOGGER_NAME = "aspectj-tracing";
    private static final Logger log = LoggerFactory.getLogger(LOGGER_NAME);
    public static boolean logIndentionEnabled = false;

    public static void main(String args[]) {
        log.info("---------------------------------------------------");
        log.info("testIndentionDisabled()");
        log.info("---------------------------------------------------");
        testIndentionDisabled();
        log.info("---------------------------------------------------");
        log.info("");
        log.info("---------------------------------------------------");
        log.info("testIndentionEnabled()");
        log.info("---------------------------------------------------");
        testIndentionEnabled();
        log.info("---------------------------------------------------");
    }

    private static void testIndentionDisabled(){
        logIndentionEnabled = false;
        PositiveValueStore value = new PositiveValueStore(10);
        try {
            value.addPositiveValue(1);
            value.addPositiveValue(-1);
        } catch (Throwable e) {
            log.error("Error in Main occurred", e);
        }
    }

    private static void testIndentionEnabled(){
        logIndentionEnabled = true;
        PositiveValueStore value = new PositiveValueStore(10);
        try {
            value.addPositiveValue(1);
            value.addPositiveValue(-1);
        } catch (Throwable e) {
            log.error("Error in Main occurred", e);
        }
    }
}