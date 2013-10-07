/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

/**
 *
 * @author caseymoncur
 */
public abstract class Command {
  private String response;
  private float responseTime;
  
  public void setResponse(String response) {
    this.response = response;
  }
  
  public String getResponse() {
    return response;
  }
  
  public void parseAckResponse(String ack) {
    if(!ack.contains("ack")) return;
    ack = ack.substring("ack ".length());
    ack = ack.substring(0, ack.indexOf(" "));
    responseTime = Float.parseFloat(ack);
  }
  
  public abstract String toCommandString();
  
  public abstract void parseResponse(String response);

}
