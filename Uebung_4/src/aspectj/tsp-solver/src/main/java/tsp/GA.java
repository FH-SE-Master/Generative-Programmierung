package tsp;

import tsp.api.Algorithm;
import tsp.api.Problem;
import tsp.api.Solution;

import java.util.Arrays;

public class GA implements Algorithm {
    private Problem problem;
    private int iterations;
    private int populationSize;
    private double mutationRate;
    private Solution[] population;
    private int currentIteration;
    private Solution best;

    public GA(Problem problem,
              int iterations,
              int populationSize,
              double mutationRate) {
        this.problem = problem;
        this.iterations = iterations;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
    }

    public Problem getProblem() { return problem; }

    public void setProblem(Problem problem) { this.problem = problem; }

    public int getIterations() { return iterations; }

    public void setIterations(int iterations) { this.iterations = iterations; }

    public int getPopulationSize() { return populationSize; }

    public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }

    public double getMutationRate() { return mutationRate; }

    public void setMutationRate(double mutationRate) { this.mutationRate = mutationRate; }

    public boolean isTerminated() { return (currentIteration >= iterations); }

    public Solution execute() {
        initialize();
        while (!isTerminated())
            iterate();
        return best;
    }

    public void initialize() {
        currentIteration = 0;
        population = new Solution[populationSize];
        for (int i = 0; i < populationSize; i++) {
            population[i] = problem.CreateRandomSolution();
            population[i].evaluate();
        }
        Arrays.sort(population);
        best = population[0];
        currentIteration++;
    }

    public void iterate() {
        Solution[] children = createChildren();
        replace(children);
        Arrays.sort(population);
        best = population[0];
        currentIteration++;
    }

    private Solution[] select(int parents) {
        int index1, index2;
        Solution[] selected = new Solution[parents];

        for (int i = 0; i < parents; i++) {
            index1 = TSPSolver.random.nextInt(population.length);
            index2 = TSPSolver.random.nextInt(population.length);
            if (population[index1].compareTo(population[index2]) < 0)
                selected[i] = population[index1];
            else
                selected[i] = population[index2];
        }
        return selected;
    }

    private Solution[] createChildren() {
        Solution[] parents;
        Solution[] children = new Solution[populationSize];

        for (int i = 0; i < children.length; i++) {
            parents = select(2);
            children[i] = createChild(parents);
        }
        return children;
    }

    private Solution createChild(Solution[] parents) {
        Solution child = parents[0].cross(parents[1]);
        if (TSPSolver.random.nextDouble() < mutationRate)
            child.mutate();
        child.evaluate();
        return child;
    }

    private void replace(Solution[] children) {
        population = children;
    }
}