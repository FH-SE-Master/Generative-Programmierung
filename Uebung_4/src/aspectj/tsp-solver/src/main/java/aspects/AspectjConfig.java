package aspects;

import java.util.Random;

/**
 * This class holds the global configuration for the aspects.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/14/17
 */
public class AspectjConfig {

    public static boolean measureRuntime = true;
    public static boolean randomSelection = false;
    public static boolean cyclicCrossover = false;
    public static boolean maximalPreservativeCrossover = false;
    public static boolean limitIterationsActive = false;
    public static long maxSolutions = 100;
}
