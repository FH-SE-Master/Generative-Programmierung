package tsp;

public interface Solution extends Comparable<Solution> {
  public double getQuality();
  public void evaluate();
  public Solution cross(Solution other);
  public void mutate();
}