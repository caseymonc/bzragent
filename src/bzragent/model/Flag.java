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
  private int x;
  private int y;
  
  public Flag(String color, String posColor, int x, int y) {
    this.color = color;
    this.posColor = posColor;
    this.x = x;
    this.y = y;
  }
  
  public static Flag parse(String response) {
    String[] parts = response.split(" ");
    return new Flag(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
  }
  
  public String getColor() {
    return color;
  }
  
  public String getPosColor() {
    return posColor;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
}
