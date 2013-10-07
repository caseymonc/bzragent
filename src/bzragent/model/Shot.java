/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

/**
 *
 * @author caseymoncur
 */
public class Shot {
  private int x;
  private int y;
  private float vX;
  private float vY;
  
  public Shot(int x, int y, float vX, float vY) {
    this.x = x;
    this.y = y;
    this.vX = vX;
    this.vY = vY;
  }
  
  public static Shot parse(String response) {
    String[] parts = response.split(" ");
    return new Shot(Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]),
                    Float.parseFloat(parts[4]));
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public float getXVelocity() {
    return vX;
  }
  
  public float getYVelocity() {
    return vY;
  }
}
