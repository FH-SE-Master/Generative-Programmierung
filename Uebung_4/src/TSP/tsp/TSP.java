package tsp;

public class TSP implements Problem {
  private int cities;
  private double[][] vertices;
  private double bestKnownQuality;
  
  public TSP(String path, double bestKnownQuality) throws Exception {
    try {
      TSPParser parser = new TSPParser(path);
      parser.parse();
      vertices = parser.getVertices();
      cities = vertices.length;
      this.bestKnownQuality = bestKnownQuality;
    } catch (Exception e) {
      throw new Exception("error creating TSP instance:" + e.getMessage());
    }
  }

  public int getCities() { return cities; }
  public double getBestKnownQuality() { return bestKnownQuality; }

  public Solution CreateRandomSolution() {
    return PathSolution.CreateRandomSolution(this);
  }

  public double getDistance(int cityA, int cityB) {
    double x1, y1, x2, y2, deltaX, deltaY, length;
    
    x1 = vertices[cityA][0];
    y1 = vertices[cityA][1];
    x2 = vertices[cityB][0];
    y2 = vertices[cityB][1];
    deltaX = x1 - x2;
    deltaY = y1 - y2;
    length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
    return length;
  }
  public long getRoundedDistance(int cityA, int cityB) {
    return Math.round(getDistance(cityA, cityB));
  }
}