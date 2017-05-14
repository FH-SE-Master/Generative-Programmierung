package tsp;

import java.util.*;

public class PathSolution implements Solution {
  private TSP tsp;
  private int[] tour;
  private double quality;
  
  public PathSolution(TSP tsp) {
    this.tsp = tsp;
    this.tour = new int[tsp.getCities()];
    this.quality = Double.MAX_VALUE;
  }

  public double getQuality() { return quality; }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append((((quality / tsp.getBestKnownQuality()) - 1) * 100) + "% ");
    builder.append("(" + quality + ")");
    builder.append("   ");
    builder.append(Arrays.toString(tour));
    return builder.toString();
  }
  
  public int compareTo(Solution s) {
    if (this.quality < s.getQuality())
      return -1;
    else if (this.quality > s.getQuality())
      return 1;
    else
      return 0;
  }

  public void evaluate() {
    quality = 0;
    for (int i = 1; i < tour.length; i++) {
      quality += tsp.getDistance(tour[i - 1], tour[i]);
    }
    quality += tsp.getDistance(tour[tour.length - 1], tour[0]);
  }

  // Order Crossover (OX)
  public Solution cross(Solution other) {
    PathSolution child = new PathSolution(tsp);
    int[] tourA, tourB, newTour;
    int length, breakPoint1, breakPoint2, index;
    boolean[] cityCopied;

    tourA = tour;
    tourB = ((PathSolution)other).tour;
    newTour = child.tour;
    length = tour.length;
    cityCopied = new boolean[length];

    breakPoint1 = TSPSolver.random.nextInt(length - 1);
    breakPoint2 = breakPoint1 + 1 + TSPSolver.random.nextInt(length - (breakPoint1 + 1));

    // copy part of first tour
    for (int i = breakPoint1; i <= breakPoint2; i++) {
      newTour[i] = tourA[i];
      cityCopied[tourA[i]] = true;
    }

    // copy remaining part of second tour
    index = 0;
    for (int i = 0; i < length; i++) {
      if (index == breakPoint1) {  // skip already copied part
        index = breakPoint2 + 1;
      }
      if (! cityCopied[tourB[i]]) {
        newTour[index] = tourB[i];
        index++;
      }
    }
    return child;
  }

  // Inversion Mutation
  public void mutate() {
    int breakPoint1, breakPoint2;
    int[] newTour = (int[])tour.clone();

    breakPoint1 = TSPSolver.random.nextInt(tour.length - 1);
    breakPoint2 = breakPoint1 + 1 + TSPSolver.random.nextInt(tour.length - (breakPoint1 + 1));

    // inverse tour between breakpoints
    for (int i = 0; i <= (breakPoint2 - breakPoint1); i++) {
      newTour[breakPoint1 + i] = tour[breakPoint2 - i];
    }
    tour = newTour;
  }

  public static Solution CreateRandomSolution(TSP tsp) {
    int index, temp;
    PathSolution solution = new PathSolution(tsp);
    
    for (int i = 0; i < solution.tour.length; i++)
      solution.tour[i] = i;

    // use Knuth Shuffle to create random permutation
    for (int i = 0; i < solution.tour.length - 1; i++) {
      index = i + TSPSolver.random.nextInt(solution.tour.length - i);
      temp = solution.tour[i];
      solution.tour[i] = solution.tour[index];
      solution.tour[index] = temp;
    }
    return solution;
  }
}