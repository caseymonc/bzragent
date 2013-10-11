/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

/**
 *
 * @author caseymoncur
 */
public class Flag {
  private String color;
  private String posColor;
  private float x;
  private float y;
  
  public Flag(String color, String posColor, float x, float y) {
    this.color = color;
    this.posColor = posColor;
    this.x = x;
    this.y = y;
  }
  
  public static Flag parse(String response) {
    String[] parts = response.split(" ");
    return new Flag(parts[1], parts[2], Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
  }
  
  public String getColor() {
    return color;
  }
  
  public String getPosColor() {
    return posColor;
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }
}
