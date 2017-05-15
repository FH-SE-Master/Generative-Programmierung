package tsp;

import aspects.util.AspectjConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsp.api.Problem;
import tsp.config.AlgorithmConfig;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // Ensure same results with same seed
            AlgorithmConfig.init();
            log.info("-------------------------------------------------------");
            log.info("testAllDisabled()");
            testAllDisabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testCountSolutionsEnabled()");
            testCountSolutionsEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testLimitSolutionsEnabled()");
            testLimitSolutionsEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testElismEnabled()");
            testElismEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testReportAndElitismEnabled()");
            testReportAndElitismEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testReportEnabled()");
            testReportEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testReportEnabledWithRandomSeed()");
            testReportEnabledWithRandomSeed();
            log.info("-------------------------------------------------------");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void testAllDisabled() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize);

        AspectjConfig.countSolutionsEnabled = false;
        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.randomSelection = false;

        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testCountSolutionsEnabled() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize);

        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.randomSelection = false;

        AspectjConfig.countSolutionsEnabled = true;

        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testLimitSolutionsEnabled() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.randomSelection = false;

        AspectjConfig.countSolutionsEnabled = true;
        AspectjConfig.limitIterationsActive = true;

        AspectjConfig.maxSolutions = 100;
        log.info("iterations={} / populationSize={} / maxSolutions={}", iterations, populationSize, AspectjConfig.maxSolutions);
        createAlgorithm(iterations, populationSize).execute();

        AspectjConfig.maxSolutions = 150;
        log.info("iterations={} / populationSize={} / maxSolutions={}", iterations, populationSize, AspectjConfig.maxSolutions);
        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testElismEnabled() throws Exception {
        final int iterations = 10;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize, AspectjConfig.maxSolutions);


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;

        AspectjConfig.elismEnabled = true;
        AspectjConfig.countSolutionsEnabled = true;

        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testReportAndElitismEnabled() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize, AspectjConfig.maxSolutions);


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.countSolutionsEnabled = false;

        AspectjConfig.elismEnabled = true;
        AspectjConfig.reportAlgorithmEnabled = true;
        AspectjConfig.reportFileName = "tsp-solver-elitism";

        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testReportEnabled() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize, AspectjConfig.maxSolutions);


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.elismEnabled = false;
        AspectjConfig.countSolutionsEnabled = false;

        AspectjConfig.reportAlgorithmEnabled = true;
        AspectjConfig.reportFileName = "tsp-solver";

        createAlgorithm(iterations, populationSize).execute();
    }

    private static void testReportEnabledWithRandomSeed() throws Exception {
        final int iterations = 1000;
        final int populationSize = 100;
        log.info("iterations={} / populationSize={}", iterations, populationSize, AspectjConfig.maxSolutions);

        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.measureRuntime = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.elismEnabled = false;
        AspectjConfig.countSolutionsEnabled = false;
        AspectjConfig.reportAlgorithmEnabled = true;

        for (int i = 1; i <= 5; i++) {
            AlgorithmConfig.init(ThreadLocalRandom.current().nextInt() + 1);
            log.info("run={}", i);
            AspectjConfig.reportFileName = "tsp-solver-random-" + i + "--";

            createAlgorithm(iterations, populationSize).execute();
        }
    }

    private static GA createAlgorithm(final int iterations,
                                      final int populationSize) throws Exception {
        Problem problem = new TSP("/ch130.tsp", 6110);
        return new GA(problem, iterations, populationSize, 0.05);
    }
}