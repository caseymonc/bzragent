/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Base;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Bases extends ListCommand {

  private ArrayList<Base> bases;
  
  public Bases() {
    bases = new ArrayList<Base>();
  }
  
  @Override
  public void parseResponseLine(String response) {
    bases.add(Base.parse(response));
  }

  @Override
  public String toCommandString() {
    return "bases\n";
  }
  
  public ArrayList<Base> getBases() {
    return bases;
  }
  
}
