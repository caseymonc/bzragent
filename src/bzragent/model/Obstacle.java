/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

/**
 *
 * @author caseymoncur
 */
public class Obstacle {
  
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  
  public Obstacle(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
  
  public static Obstacle parse(String response) {
    String[] parts = response.split(" ");
    return new Obstacle(Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]));
  } 
  
  public int getX1() {
    return x1;
  }
  
  public int getY1() {
    return y1;
  }
  
  public int getX2() {
    return x2;
  }
  
  public int getY2() {
    return y2;
  }
}
