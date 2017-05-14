package tsp;

public interface Algorithm {
  public void setProblem(Problem problem);
  public Problem getProblem();
  public void setIterations(int iterations);
  public int getIterations();

  public boolean isTerminated();

  public Solution execute();
  public void initialize();
  public void iterate();
}