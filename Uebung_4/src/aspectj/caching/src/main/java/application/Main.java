package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class Main {

    public static boolean LoggingEnabled = false;
    public static boolean CachingEnabled = false;
    public static boolean RuntimeMeasurementEnabled = false;
    public static final String LOGGER_NAME = "aspect-caching";

    private static final Logger log = LoggerFactory.getLogger(LOGGER_NAME);

    public static void main(String args[]) {
        final int n = 45;
        final int m = 10;
        log.info("-------------------------------------------------------------------");
        log.info("testAllDisabled()");
        log.info("-------------------------------------------------------------------");
        testAllDisabled(n, m);
        log.info("-------------------------------------------------------------------");
        log.info("");
        log.info("-------------------------------------------------------------------");
        log.info("testRuntimeMeasurementEnabled()");
        log.info("-------------------------------------------------------------------");
        testRuntimeMeasurementEnabled(n, m);
        log.info("-------------------------------------------------------------------");
        log.info("");
        log.info("-------------------------------------------------------------------");
        log.info("");
        log.info("testRuntimeMeasurementAndLoggingEnabled()");
        log.info("-------------------------------------------------------------------");
        testRuntimeMeasurementAndLoggingEnabled(n, m);
        log.info("-------------------------------------------------------------------");
        log.info("");
        log.info("-------------------------------------------------------------------");
        log.info("");
        log.info("testAllEnabled()");
        log.info("-------------------------------------------------------------------");
        testAllEnabled(n, m);
        log.info("-------------------------------------------------------------------");
    }

    private static void testAllDisabled(final int n,
                                        final int m) {
        LoggingEnabled = false;
        CachingEnabled = false;
        RuntimeMeasurementEnabled = false;

        log.info("Starting: measurement={} / cachingEnabled={} / logRecursiveCallsEnabled={}", RuntimeMeasurementEnabled, CachingEnabled, LoggingEnabled);
        log.info("          n={} / m={}", n,m);
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(n, m));
    }

    private static void testRuntimeMeasurementEnabled(final int n,
                                                      final int m) {
        LoggingEnabled = false;
        CachingEnabled = false;
        RuntimeMeasurementEnabled = true;

        log.info("Starting: measurement={} / cachingEnabled={} / logRecursiveCallsEnabled={}", RuntimeMeasurementEnabled, CachingEnabled, LoggingEnabled);
        log.info("          n={} / m={}", n,m);
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(n, m));
    }

    private static void testRuntimeMeasurementAndLoggingEnabled(final int n,
                                                                final int m) {
        CachingEnabled = false;
        LoggingEnabled = true;
        RuntimeMeasurementEnabled = true;

        log.info("Starting: measurement={} / cachingEnabled={} / logRecursiveCallsEnabled={}", RuntimeMeasurementEnabled, CachingEnabled, LoggingEnabled);
        log.info("          n={} / m={}", n,m);
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(n, m));
    }

    private static void testAllEnabled(final int n,
                                       final int m) {
        LoggingEnabled = true;
        CachingEnabled = true;
        RuntimeMeasurementEnabled = true;

        log.info("Starting: measurement={} / cachingEnabled={} / logRecursiveCallsEnabled={}", RuntimeMeasurementEnabled, CachingEnabled, LoggingEnabled);
        log.info("          n={} / m={}", n,m);
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(n, m));
    }
}
