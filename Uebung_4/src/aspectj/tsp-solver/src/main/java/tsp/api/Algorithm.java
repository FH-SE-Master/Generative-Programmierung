package tsp.api;

public interface Algorithm {
    public Problem getProblem();

    public void setProblem(Problem problem);

    public int getIterations();

    public void setIterations(int iterations);

    public boolean isTerminated();

    public Solution execute();

    public void initialize();

    public void iterate();
}