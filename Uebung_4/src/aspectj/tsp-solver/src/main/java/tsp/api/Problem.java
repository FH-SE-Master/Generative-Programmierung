package tsp.api;

import tsp.api.Solution;

public interface Problem {
    public Solution CreateRandomSolution();
}