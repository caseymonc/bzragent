/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

/**
 *
 * @author caseymoncur
 */
public abstract class ListCommand extends Command {
  public void parseResponse(String response) {
    String[] stringList = response.split("\n");
    for(int i = 0; i < stringList.length; i++) {
      parseResponseLine(stringList[i]);
    }
  }
  
  public abstract void parseResponseLine(String response);
}
