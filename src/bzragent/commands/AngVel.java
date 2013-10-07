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
public class AngVel extends TankCommand {
  private final float angularVelocity;
  
  public AngVel(Tank tank, float angularVelocity) {
    super(tank);
    this.angularVelocity = angularVelocity;
  }

  @Override
  public String toCommandString() {
    return "angvel " + getTank().getIndex() + " " + angularVelocity + "\n";
  }
  
}
