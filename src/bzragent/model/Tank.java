/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

import javax.swing.Timer;

/**
 *
 * @author caseymoncur
 */
public class Tank {
  private int index;
  private String callsign;
  private String status;
  private int shotsAvailable;
  private float timeToReload;
  private String flag;
  private float x;
  private float y;
  private float angle;
  private float velocityX;
  private float velocityY;
  private float angularVelocity;
  
  public Tank() {
    this.index = 0;
    this.callsign = "Test";
    this.status = "Done";
    this.shotsAvailable = 20;
    this.timeToReload = 5;
    this.flag = "blue";
    this.x = 0;
    this.y = 0;
    this.angle = 0;
    this.velocityX = 0;
    this.velocityY = 0;
    this.angularVelocity = 0;
  }
  
  public Tank(int index, String callsign, String status, int shotsAvailable, 
               float timeToReload, String flag, float x, float y, float angle,
               float velocityX, float velocityY, float angularVelocity) {
    this.index = index;
    this.callsign = callsign;
    this.status = status;
    this.shotsAvailable = shotsAvailable;
    this.timeToReload = timeToReload;
    this.flag = flag;
    this.x = x;
    this.y = y;
    this.angle = angle;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
    this.angularVelocity = angularVelocity;
  }
  
  public int getIndex() {
    return index;
  }
  
  public String getCallsign() {
    return callsign;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public int getShotsAvailable() {
    return this.shotsAvailable;
  }
  
  public void setShotsAvailable(int shotsAvailable) {
    this.shotsAvailable = shotsAvailable;
  }
  
  public float getTimeToReload() {
    return this.timeToReload;
  }
  
  public void setTimeToReload(float timeToReload) {
    this.timeToReload = timeToReload;
  }
  
  public String getFlag() {
    return this.flag;
  }
  
  public boolean hasFlag() {
    return this.flag != null && !this.flag.equals("-");
  }
  
  public void setFlag(String flag) {
    this.flag = flag;
  }
  
  public float getX() {
    return this.x;
  }
  
  public void setX(float x) {
    this.x = x;
  }
  
  public float getY() {
    return this.y;
  }
  
  public void setY(float y) {
    this.y = y;
  }
  
  public float getAngle() {
    return this.angle;
  }
  
  public void setAngle(float angle) {
    this.angle = angle;
  }
  
  public float getVelocityX() {
    return this.velocityX;
  }
  
  public void setVelocityX(float velocityX) {
    this.velocityX = velocityX;
  }
  
  public float getVelocityY() {
    return this.velocityY;
  }
  
  public void setVelocityY(float velocityY) {
    this.velocityY = velocityY;
  }
  
  public float getAngularVelocity() {
    return this.angularVelocity;
  }
  
  public void setAngularVelocity(float angularVelocity) {
    this.angularVelocity = angularVelocity;
  }
  
  public static Tank parse(String response) {
    response = response.substring(response.indexOf(" ") + 1);
    String toParse = response.substring(0, response.indexOf(" ")).trim();
    int index = Integer.parseInt(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    String callsign = toParse;
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    String status = toParse;
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    int shotsAvailable = Integer.parseInt(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float timeToReload = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    String flag = toParse;
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float x = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float y = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float angle = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float velocityX = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response.substring(0, response.indexOf(" "));
    float velocityY = Float.parseFloat(toParse);
    
    response = response.substring(response.indexOf(" ") + 1).trim();
    toParse = response;
    float angularVelocity = Float.parseFloat(toParse);
    return new Tank(index, callsign, status, shotsAvailable, timeToReload, flag, 
            x, y, angle, velocityX, velocityY, angularVelocity);
  }

    public float getAngularNextVelocity(float angleToGoTo) {
        return 5;
    }
}
