/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Flag;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Flags extends ListCommand {

  private ArrayList<Flag> flags = new ArrayList<Flag>();
  
  @Override
  public void parseResponseLine(String response) {
    flags.add(Flag.parse(response));
  }

  @Override
  public String toCommandString() {
    return "flags\n";
  }
  
  public ArrayList<Flag> getFlags() {
    return flags;
  }
}
