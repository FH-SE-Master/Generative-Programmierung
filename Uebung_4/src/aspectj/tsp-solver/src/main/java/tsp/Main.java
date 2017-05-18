package tsp;

import aspects.util.AspectjConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsp.api.Problem;
import tsp.config.AlgorithmConfig;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int ITERATIONS = 50;
    private static final int POPULATION_SIZE = 100;
    private static final int RANDOM_RUN = 3;
    private static final Logger log = LoggerFactory.getLogger(AspectjConfig.LOGGER_NAME);

    public static void main(String[] args) {
        // Always enabled
        AspectjConfig.measureRuntime = true;
        try {
            // Ensure same results with same seed
            AlgorithmConfig.init();
            log.info("-------------------------------------------------------");
            log.info("testAllDisabled()");
            log.info("-------------------------------------------------------");
            testAllDisabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testCountSolutionsEnabled()");
            log.info("-------------------------------------------------------");
            testCountSolutionsEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testLimitSolutionsEnabled()");
            log.info("-------------------------------------------------------");
            testLimitSolutionsEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testElitismEnabled()");
            log.info("-------------------------------------------------------");
            testElitismEnabled();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testReportAndElitismEnabledWithRandomSeed()");
            log.info("-------------------------------------------------------");
            testReportAndElitismEnabledWithRandomSeed();
            log.info("-------------------------------------------------------");
            log.info("");
            log.info("-------------------------------------------------------");
            log.info("testReportEnabledWithRandomSeed()");
            log.info("-------------------------------------------------------");
            testReportEnabledWithRandomSeed();
            log.info("-------------------------------------------------------");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void testAllDisabled() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE);

        AspectjConfig.countSolutionsEnabled = false;
        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elitismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.randomSelection = false;

        createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
    }

    private static void testCountSolutionsEnabled() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE);

        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elitismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.randomSelection = false;

        AspectjConfig.countSolutionsEnabled = true;

        createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
        log.info("iterations={} / populationSize={}", 75, 550);
        createAlgorithm(75, 550).execute();
    }

    private static void testLimitSolutionsEnabled() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE);

        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.elitismEnabled = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.randomSelection = false;

        AspectjConfig.countSolutionsEnabled = true;
        AspectjConfig.limitIterationsActive = true;

        AspectjConfig.maxSolutions = 100;
        log.info("iterations={} / populationSize={} / maxSolutions={}", ITERATIONS, POPULATION_SIZE, AspectjConfig.maxSolutions);
        createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();

        AspectjConfig.maxSolutions = 150;
        log.info("iterations={} / populationSize={} / maxSolutions={}", ITERATIONS, POPULATION_SIZE, AspectjConfig.maxSolutions);
        createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
    }

    private static void testElitismEnabled() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE, AspectjConfig.maxSolutions);


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.reportAlgorithmEnabled = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;

        AspectjConfig.elitismEnabled = true;
        AspectjConfig.countSolutionsEnabled = true;

        createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
    }

    private static void testReportAndElitismEnabledWithRandomSeed() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE, AspectjConfig.maxSolutions);


        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.countSolutionsEnabled = false;

        AspectjConfig.elitismEnabled = true;
        AspectjConfig.reportAlgorithmEnabled = true;

        for (int i = 1; i <= RANDOM_RUN; i++) {
            AlgorithmConfig.init(ThreadLocalRandom.current().nextInt() + 1);
            log.info("-------------------------------------------------------");
            log.info("random run={}", i);
            log.info("-------------------------------------------------------");
            AspectjConfig.reportFileName = "tsp-solver-elitism-random-" + i + "--";

            createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
        }
    }

    private static void testReportEnabledWithRandomSeed() throws Exception {
        log.info("iterations={} / populationSize={}", ITERATIONS, POPULATION_SIZE, AspectjConfig.maxSolutions);

        AspectjConfig.cyclicCrossover = false;
        AspectjConfig.maximalPreservativeCrossover = false;
        AspectjConfig.randomSelection = false;
        AspectjConfig.limitIterationsActive = false;
        AspectjConfig.elitismEnabled = false;
        AspectjConfig.countSolutionsEnabled = false;

        AspectjConfig.reportAlgorithmEnabled = true;

        for (int i = 1; i <= RANDOM_RUN; i++) {
            AlgorithmConfig.init(ThreadLocalRandom.current().nextInt() + 1);
            log.info("-------------------------------------------------------");
            log.info("random run={}", i);
            log.info("-------------------------------------------------------");
            AspectjConfig.reportFileName = "tsp-solver-random-" + i + "--";

            createAlgorithm(ITERATIONS, POPULATION_SIZE).execute();
        }
    }

    private static GA createAlgorithm(final int iterations,
                                      final int populationSize) throws Exception {
        Problem problem = new TSP("/ch130.tsp", 6110);
        return new GA(problem, iterations, populationSize, 0.05);
    }
}