package aspects;

import tsp.*;
import tsp.api.Solution;
import tsp.config.AlgorithmConfig;

public privileged aspect RandomSelection {
    Solution[] around(GA ga,
                      int parents):
            if(aspects.util.AspectjConfig.randomSelection) &&
                    Pointcuts.select(ga, parents) {
        Solution[] selected = new PathSolution[parents];
        for (int i = 0; i < selected.length; i++)
            selected[i] = ga.population[AlgorithmConfig.random.nextInt(ga.population.length)];
        return selected;
    }
}
