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

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
        PositiveValueStore value = new PositiveValueStore(10);
        try {
            value.addPositiveValue(1);
            value.addPositiveValue(-1);
        } catch (Throwable e) {
            log.error("Error in Main occurred", e);
        }
    }
}