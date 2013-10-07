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
public abstract class TankCommand extends Command{
  private Tank tank;
  private boolean success;
  
  public TankCommand(Tank tank) {
    this.tank = tank;
  }
  
  public Tank getTank() {
    return tank;
  }
  
  @Override
  public void parseResponse(String response) {
    if(response.indexOf("ok") == 0) {
      success = true;
    } else if(response.indexOf("fail") == 0) {
      success = false;
    }
  }
  
  public boolean wasSuccessful() {
    return success;
  }
}
