/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Shot;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Shots extends ListCommand {

  private ArrayList<Shot> shots = new ArrayList<Shot>();
  
  @Override
  public void parseResponseLine(String response) {
    shots.add(Shot.parse(response));
  }

  @Override
  public String toCommandString() {
    return "shots\n";
  }
  
  public ArrayList<Shot> getShots() {
    return shots;
  }
  
}
