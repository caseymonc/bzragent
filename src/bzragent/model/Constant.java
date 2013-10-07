/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.model;

/**
 *
 * @author caseymoncur
 */
public class Constant {
  
  private String name;
  private String value;
  
  public Constant(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  public static Constant parse(String response) {
    String[] parts = response.split(" ");
    return new Constant(parts[1], parts[2]);
  }
  
  public String getName() {
    return name;
  }
  
  public String getValue() {
    return value;
  }
}
