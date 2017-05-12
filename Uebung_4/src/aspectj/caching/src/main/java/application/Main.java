package application;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class Main {

    public static boolean ActivateLogging = false;
    public static boolean ActivateCaching = false;
    private static final StopWatch watch = new StopWatch();

    public static void main(String args[]) {
        System.out.println("Starting calculations");

        testCalculation(10, 7);
        testCalculation(45, 10);

        System.out.println("Logging activated");
        ActivateLogging = true;
        testCalculation(10, 7);
        testCalculation(45, 10);

        System.out.println("Caching activated");
        ActivateCaching = true;
        testCalculation(10, 7);
        testCalculation(45, 10);
    }

    private static void testCalculation(final int n,
                                        final int m) {
        System.out.println(String.format("Calculating binom for n=%d, m=%d", n, m));

        watch.reset();
        watch.start();
        final long result = BinomialCoefficient.calculate(n, m);
        watch.stop();

        System.out.println(String.format("Calculated binom result: %d in millis=%d", result, watch.getTime()));
    }
}
