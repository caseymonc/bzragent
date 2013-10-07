/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Tank;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class MyTanks extends ListCommand {

  private ArrayList<Tank> tanks = new ArrayList<Tank>();
  
  @Override
  public void parseResponseLine(String response) {
    tanks.add(Tank.parse(response));
  }

  @Override
  public String toCommandString() {
    return "mytanks\n";
  }
  
  public ArrayList<Tank> getTanks() {
    return tanks;
  }
  
}
