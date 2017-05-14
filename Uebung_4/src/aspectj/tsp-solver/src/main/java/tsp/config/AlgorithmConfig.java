package tsp.config;

import java.util.Random;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/14/17
 */
public class AlgorithmConfig {

    public static Random random;
    public static final long DEFAULT_SEED = 10;

    public static void init() {
        init(DEFAULT_SEED);
    }

    public static void init(long seed) {
        random = new Random(seed);
    }
}
