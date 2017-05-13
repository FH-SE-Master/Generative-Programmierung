package aspects;

import tsp.*;
import tsp.api.Solution;


public aspect Pointcuts {
    public pointcut execute(): call(Solution Algorithm.execute());

    public pointcut select(GA ga,
                           int parents):
            call(Solution[] GA.select(..)) &&
                    target(ga) &&
                    args(parents);

    public pointcut cross(Solution solution,
                          Solution other):
            call(Solution Solution.cross(..)) &&
                    target(solution) &&
                    args(other);
}