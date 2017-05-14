package aspects;

import tsp.api.Solution;
import tsp.TSPSolver;

import java.util.Date;

public aspect MeasureRuntime {
    Solution around(): if(AspectjConfig.measureRuntime) &&
            Pointcuts.execute() {
        Solution best;

        Date start = new Date();
        best = proceed();
        Date end = new Date();

        System.out.println("Runtime (in ms): " + (end.getTime() - start.getTime()));
        return best;
    }
}