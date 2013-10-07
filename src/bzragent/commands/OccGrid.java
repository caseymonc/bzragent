/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Tank;

/**
 *
 * @author caseymoncur
 */
public class OccGrid extends TankCommand {

  public OccGrid(Tank tank) {
    super(tank);
  }
  
  @Override
  public void parseResponse(String response) {
    
  }
  
  @Override
  public String toCommandString() {
    return "occgrid " + getTank().getIndex() + "\n";
  }
  
}
