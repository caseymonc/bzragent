/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Constant;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Constants extends ListCommand {

  private ArrayList<Constant> constants = new ArrayList<Constant>();
  
  @Override
  public void parseResponseLine(String response) {
    constants.add(Constant.parse(response));
  }

  @Override
  public String toCommandString() {
    return "constants\n";
  }
  
  public ArrayList<Constant> getConstants() {
    return constants;
  }
}
