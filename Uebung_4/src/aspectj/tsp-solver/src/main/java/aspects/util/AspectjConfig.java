package aspects.util;

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

    public static boolean elitismEnabled = false;
    public static boolean countSolutionsEnabled = false;
    public static boolean limitIterationsActive = false;
    public static boolean reportAlgorithmEnabled = false;
    public static long maxSolutions = 100;
    public static String reportFileName = "tsp-solver";

    public static final String LOGGER_NAME = "aspectj-tsp-solver";
}
