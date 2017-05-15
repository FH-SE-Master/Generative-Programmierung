package aspects;

import tsp.*;
import tsp.api.Solution;
import tsp.config.AlgorithmConfig;


public privileged aspect MaximalPreservativeCrossover {
    Solution around(Solution solution,
                    Solution other):
            if (aspects.util.AspectjConfig.maximalPreservativeCrossover) &&
                    Pointcuts.cross(solution, other) {
        PathSolution child = new PathSolution(((PathSolution) solution).tsp);
        int[] tourA, tourB, newTour;
        int length, breakPoint1, breakPoint2, subtourLength, index;
        boolean[] cityCopied;

        tourA = ((PathSolution) solution).tour;
        tourB = ((PathSolution) other).tour;
        newTour = child.tour;
        length = tourA.length;
        cityCopied = new boolean[length];

        if (length >= 20) {  // length of subtour must be >= 10 and <= length / 2
            breakPoint1 = AlgorithmConfig.random.nextInt(length - 9);
            subtourLength = 10 + AlgorithmConfig.random.nextInt((Math.min(length / 2, length - breakPoint1) + 1) - 10);
            breakPoint2 = breakPoint1 + subtourLength - 1;
        } else {
            breakPoint1 = AlgorithmConfig.random.nextInt(length - 1);
            breakPoint2 = breakPoint1 + 1 + AlgorithmConfig.random.nextInt(length - (breakPoint1 + 1));
        }

        // copy part of first route
        index = 0;
        for (int i = breakPoint1; i <= breakPoint2; i++) {
            newTour[index] = tourA[i];
            cityCopied[newTour[index]] = true;
            index++;
        }

        // copy remaining part of second route
        for (int i = 0; i < tourB.length; i++) {
            if (!cityCopied[tourB[i]]) {
                newTour[index] = tourB[i];
                index++;
            }
        }
        return child;
    }
}
