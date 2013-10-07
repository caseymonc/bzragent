/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Obstacle;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Obstacles extends ListCommand {

  private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
  
  @Override
  public void parseResponseLine(String response) {
    obstacles.add(Obstacle.parse(response));
  }

  @Override
  public String toCommandString() {
    return "obstacles\n";
  }
  
  public ArrayList<Obstacle> getObstacles() {
    return obstacles;
  }
  
}
