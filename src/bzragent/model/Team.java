/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

/**
 *
 * @author caseymoncur
 */
public class Team {
  private int playerCount;
  private String color;
  
  public Team(String color, int playerCount) {
    this.color = color;
    this.playerCount = playerCount;
  }
  
  public static Team parse(String response) {
    String[] parts = response.split(" ");
    return new Team(parts[1], Integer.parseInt(parts[2]));
  }
  
  public String getColor() {
    return color;
  }
  
  public int getPlayerCount() {
    return playerCount;
  }
}
