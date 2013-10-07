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
public class Shoot extends TankCommand{
  
  public Shoot(Tank tank) {
    super(tank);
  }
  
  @Override
  public String toCommandString() {
    return "shoot " + getTank().getIndex() + "\n";
  }
}
