/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import bzragent.model.Team;
import java.util.ArrayList;

/**
 *
 * @author caseymoncur
 */
public class Teams extends ListCommand {

  private ArrayList<Team> teams = new ArrayList<Team>();
  
  @Override
  public void parseResponseLine(String response) {
    teams.add(Team.parse(response));
  }

  @Override
  public String toCommandString() {
    return "teams\n";
  }
  
  public ArrayList<Team> getTeams() {
    return teams;
  }
  
}
