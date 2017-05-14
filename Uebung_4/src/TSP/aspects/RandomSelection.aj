package aspects;

import tsp.*;

public privileged aspect RandomSelection {
  Solution[] around(GA ga, int parents) :
    if(TSPSolver.randomSelection) &&
    Pointcuts.select(ga, parents) {
    Solution[] selected = new PathSolution[parents];
    for (int i = 0; i < selected.length; i++)
      selected[i] = ga.population[TSPSolver.random.nextInt(ga.population.length)];
    return selected;
  }
}
