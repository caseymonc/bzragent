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
public class Speed extends TankCommand{
  private float speed;

  public Speed(Tank tank, float speed) {
    super(tank);
    this.speed = speed;
  }
  
  @Override
  public String toCommandString() {
    return "speed " + getTank().getIndex() + " " + speed + "\n";
  }
  
}
