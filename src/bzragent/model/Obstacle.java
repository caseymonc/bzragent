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
  
  private float x1;
  private float y1;
  private float x2;
  private float y2;
  
  public Obstacle(float x1, float y1, float x2, float y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
  
  public static Obstacle parse(String response) {
    String[] parts = response.split(" ");
    return new Obstacle(Float.parseFloat(parts[1]),
                        Float.parseFloat(parts[2]),
                        Float.parseFloat(parts[3]),
                        Float.parseFloat(parts[4]));
  } 
  
  public float getX() {
      return (x1 + x2)/2;
  }
  
  public float getY() {
      return (y1 + y2)/2;
  }
  
  public float getX1() {
    return x1;
  }
  
  public float getY1() {
    return y1;
  }
  
  public float getX2() {
    return x2;
  }
  
  public float getY2() {
    return y2;
  }
}
