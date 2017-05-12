package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static boolean ActivateLogging = false;
    public static boolean ActivateCaching = false;

    public static void main(String args[]) {
        log.info("-------------------------------------------------------------------");
        log.info("Start");
        log.info("Starting: cachingEnabled={} / logRecursiveCallsEnabled={}", ActivateCaching, ActivateLogging);
        log.info("BinomialCoefficient.calculate(10, 7): {}", BinomialCoefficient.calculate(10, 7));
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(45, 10));
        log.info("End");
        log.info("-------------------------------------------------------------------");

        ActivateLogging = true;
        log.info("-------------------------------------------------------------------");
        log.info("Start");
        log.info("Starting: cachingEnabled={} / logRecursiveCallsEnabled={}", ActivateCaching, ActivateLogging);
        log.info("BinomialCoefficient.calculate(10, 7): {}", BinomialCoefficient.calculate(10, 7));
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(45, 10));
        log.info("End");
        log.info("-------------------------------------------------------------------");

        ActivateCaching = true;
        log.info("-------------------------------------------------------------------");
        log.info("Start");
        log.info("Starting: cachingEnabled={} / logRecursiveCallsEnabled={}", ActivateCaching, ActivateLogging);
        log.info("BinomialCoefficient.calculate(10, 7): {}", BinomialCoefficient.calculate(10, 7));
        log.info("BinomialCoefficient.calculate(45, 10): {}", BinomialCoefficient.calculate(45, 10));
        log.info("End");
        log.info("-------------------------------------------------------------------");
    }
}
