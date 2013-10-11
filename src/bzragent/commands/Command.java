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
  
  public boolean parseAckResponse(String ack) {
    if(!ack.contains("ack")) return false;
    ack = ack.substring("ack ".length());
    ack = ack.substring(0, ack.indexOf(" "));
    responseTime = Float.parseFloat(ack);
    return true;
  }
  
  public abstract String toCommandString();
  
  public abstract void parseResponse(String response);

    public boolean canParse(String s) {
        boolean canParse = true;
        canParse &= (s.indexOf("ack") == 0);
        s = s.substring(s.indexOf("\n") + 1);
        
        if(s.startsWith("begin")){
            canParse &= s.contains("end");
        }else{
            canParse &= s.contains("\n");
        }
        
        return canParse;
    }

}
