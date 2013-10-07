/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import bzragent.commands.AngVel;
import bzragent.commands.Bases;
import bzragent.commands.Constants;
import bzragent.commands.Flags;
import bzragent.commands.MyTanks;
import bzragent.commands.Obstacles;
import bzragent.commands.Shoot;
import bzragent.commands.Shots;
import bzragent.commands.Speed;
import bzragent.commands.Teams;
import bzragent.model.Base;
import bzragent.model.Constant;
import bzragent.model.Flag;
import bzragent.model.Obstacle;
import bzragent.model.Shot;
import bzragent.model.Tank;
import bzragent.model.Team;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caseymoncur
 */
public class Bzragent {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if(args.length > 0 && args[0].equals("test")){
      Test();
    } else {
      try {
        BZRSocket socket = new BZRSocket("localhost", 4056);
      } catch (UnknownHostException ex) {
        Logger.getLogger(Bzragent.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
        Logger.getLogger(Bzragent.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  public static void Test() {
    boolean success = true;
    //Run Tests for now
    BZRSocket socket = new BZRSocket();
    
    Shoot shoot = new Shoot(new Tank()); 
    socket.parseResponse("ack 111.456 shoot 1\nok Success", shoot);
    success &= shoot.wasSuccessful();
    
    AngVel angvel = new AngVel(new Tank(), 45); 
    socket.parseResponse("ack 111.456 angvel 1\nok Success", angvel);
    success &= angvel.wasSuccessful();
    
    Speed speed = new Speed(new Tank(), 1); 
    socket.parseResponse("ack 111.456 speed 1\nok Success", speed);
    success &= speed.wasSuccessful();
    
    shoot = new Shoot(new Tank()); 
    socket.parseResponse("ack 111.456 shoot 1\nfail Success", shoot);
    success &= !shoot.wasSuccessful();
    
    angvel = new AngVel(new Tank(), 45); 
    socket.parseResponse("ack 111.456 angvel 1\nfail Success", angvel);
    success &= !angvel.wasSuccessful();
    
    speed = new Speed(new Tank(), 1); 
    socket.parseResponse("ack 111.456 speed 1\nfail Success", speed);
    success &= !speed.wasSuccessful();
    
    MyTanks myTanks = new MyTanks();
    String myTanksString = "ack 111.456 mytanks\n";
    for(int i = 0; i < 10; i++) {
      myTanksString += "tank " + i + " tank" + i + " alive 15 15.5 blue 0.0 0.2 0.0 4.4 4.4 50\n";
    }
    socket.parseResponse(myTanksString, myTanks);
    ArrayList<Tank> tanks = myTanks.getTanks();
    success &= tanks.size() == 10;
    success &= tanks.get(0).getIndex() == 0;
    success &= tanks.get(0).getFlag().equals("blue");
    
    Bases bases = new Bases();
    String basesString = "ack 111.456 bases\n";
    for(int i = 0; i < 4; i++) {
      basesString += "base blue 4 5 6 7\n";
    }
    socket.parseResponse(basesString, bases);
    ArrayList<Base> basesArray = bases.getBases();
    success &= basesArray.size() == 4;
    success &= basesArray.get(0).getX1() == 4;
    success &= basesArray.get(0).getColor().equals("blue");
    
    Teams teams = new Teams();
    String teamsString = "ack 111.456 teams\n";
    for(int i = 0; i < 4; i++) {
      teamsString += "team blue 15\n";
    }
    socket.parseResponse(teamsString, teams);
    ArrayList<Team> teamsArray = teams.getTeams();
    success &= teamsArray.size() == 4;
    success &= teamsArray.get(0).getPlayerCount() == 15;
    success &= teamsArray.get(0).getColor().equals("blue");
    
    Obstacles obstacles = new Obstacles();
    String obString = "ack 111.456 obstacles\n";
    for(int i = 0; i < 15; i++) {
      obString += "obstacle 1 2 3 1\n";
    }
    socket.parseResponse(obString, obstacles);
    ArrayList<Obstacle> obs = obstacles.getObstacles();
    success &= obs.size() == 15;
    success &= obs.get(0).getX1() == 1;
    
    Flags flags = new Flags();
    String flagsString = "ack 111.456 teams\n";
    for(int i = 0; i < 4; i++) {
      flagsString += "flag blue red 3 5\n";
    }
    socket.parseResponse(flagsString, flags);
    ArrayList<Flag> flagArray = flags.getFlags();
    success &= flagArray.size() == 4;
    success &= flagArray.get(0).getColor().equals("blue");
    success &= flagArray.get(0).getPosColor().equals("red");
    success &= flagArray.get(0).getX() == 3;
    success &= flagArray.get(0).getY() == 5;
    
    Shots shots = new Shots();
    String shotsS = "ack 111.456 shots\n";
    for(int i = 0; i < 7; i++) {
      shotsS += "shot 1 2 4.5 1.1\n";
    }
    socket.parseResponse(shotsS, shots);
    ArrayList<Shot> shotsA = shots.getShots();
    success &= shotsA.size() == 7;
    success &= shotsA.get(0).getX() == 1;
    success &= shotsA.get(0).getY() == 2;
    success &= shotsA.get(0).getXVelocity() == 4.5f;
    success &= shotsA.get(0).getYVelocity() == 1.1f;

    Constants cons = new Constants();
    String consString = "ack 111.456 shots\n";
    for(int i = 0; i < 1; i++) {
      consString += "constant test this\n";
    }
    socket.parseResponse(consString, cons);
    ArrayList<Constant> consA = cons.getConstants();
    success &= consA.size() == 1;
    success &= consA.get(0).getName().equals("test");
    success &= consA.get(0).getValue().equals("this");
    
    if(!success) System.out.println("Failed");
    else System.out.println("Success");
  }
}
