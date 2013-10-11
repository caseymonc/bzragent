/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import static bzragent.Agent.REFRESH_TANKS_INTERVAL;
import bzragent.commands.AngVel;
import bzragent.commands.Bases;
import bzragent.commands.Flags;
import bzragent.commands.Obstacles;
import bzragent.commands.Shoot;
import bzragent.commands.Speed;
import bzragent.model.Base;
import bzragent.model.Flag;
import bzragent.model.Obstacle;
import bzragent.model.Tank;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author cmoncur
 */
public class PotentialFieldsAgent extends Agent{

    private static final int SHOOT_INTERVAL = 2000;
        
    private ArrayList<TankTimer> tanks;
    private Base home;
    private Set<Obstacle> obstacles;
    private boolean haveRedFlag = false;
    private boolean haveGreenFlag = false;
    private boolean havePurpleFlag = false;
    private String color;
    
    public PotentialFieldsAgent(BZRSocket socket, String color) throws IOException {
        super(socket);
        this.color = color;
        tanks = new ArrayList<TankTimer>();
        
        Obstacles obsCommand = new Obstacles();
        socket.sendCommand(obsCommand);
        obstacles = new HashSet<Obstacle>();
        for(Obstacle o : obsCommand.getObstacles()) {
            obstacles.add(o);
        }
    }
    
    @Override
    public void start() {
        for(Tank tank : getTanks()) {
            TankTimer timer = new TankTimer(tank.getCallsign());
            tanks.add(timer);
            timer.start();
        }
    }
    
    private class TankTimer {
        private Timer shootTimer;
        private Timer turnTimer;
        private String callsign;
        private float lastAngle;
        
        public TankTimer(final String callsign) {
            this.callsign = callsign;
            startTank();
            
            shootTimer = new Timer(getNextShootDelay(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shoot();
                    shootTimer.stop();
                    shootTimer = new Timer(getNextShootDelay(), this);
                    shootTimer.start();
                }
            });
            stopTank();
            startTank();
            startTurnTimer();
        }
        
        public void startTurnTimer() {
            lastAngle = getTank(callsign).getAngle();
            
            turnTimer = new Timer(REFRESH_TANKS_INTERVAL, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startTank();
                    Tank tank = getTank(callsign);
                    float angle = potentialField(tank.getX(), tank.getY(), tank.getAngle());
                    float angvel = PDController.getAngVel(angle, lastAngle);
                    setTankAngVel(angvel);
                    lastAngle = angle;
                }
            });
            shootTimer.start();
            turnTimer.start();
        }
        
        public int getNextShootDelay() {
            int nextDelay = (int)((new Random().nextFloat() + 1.5f) * 1000);
            return nextDelay;
        }
        
        public int getNextMoveDelay() {
            int nextDelay = (new Random().nextInt(5) + 3) * 1000;
            return nextDelay;
        }
        
        public void start() {
            
        }
        
        public void stop() {
            shootTimer.stop();
            turnTimer.stop();
            try{
                Speed speedCommand = new Speed(getTank(callsign), 0);
                getSocket().sendCommand(speedCommand);
            }catch(IOException e) {
                
            }
        }
        
        private void shoot() {
            //if(haveFlag) return;
            try{
                Shoot shootCommand = new Shoot(getTank(callsign));
                getSocket().sendCommand(shootCommand);
            }catch(IOException e) {

            }
        }
        
        private void stopTank() {
            try{
                Speed stopCommand = new Speed(getTank(callsign), 0);
                getSocket().sendCommand(stopCommand);
            }catch(IOException e) {

            }
        }
        
        private void startTank() {
            try{
                Speed stopCommand = new Speed(getTank(callsign), 1.0f);
                getSocket().sendCommand(stopCommand);
            }catch(IOException e) {

            }
        }
        
        private void setTankAngVel(float angvel) {
            AngVel angularVelocityCommand = new AngVel(getTank(callsign), angvel);
            try {
                getSocket().sendCommand(angularVelocityCommand);
            } catch (IOException ex) {
                Logger.getLogger(DumbAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public float potentialField(float x, float y, float angle) {
            if(angle < 0) angle += 2 * Math.PI;
            angle -= Math.PI;
            
            Tank tank = getTank(callsign);
            float signed_angle;
            float goalX;
            float goalY;
            
            if(tank.getIndex() < 15) {
                if(!tank.hasFlag() && !haveRedFlag){
                    Flag goal = getFlag("red");
                    goalX = goal.getX();
                    goalY = goal.getY();
                }else if(!tank.hasFlag()){
                    goalX = getBase("red").getX();
                    goalY = getBase("red").getY();
                } else {
                    goalX = getBase(color).getX();
                    goalY = getBase(color).getY();
                }
            }else if(tank.getIndex() < 6) {
                if(!tank.hasFlag() && !haveGreenFlag){
                    Flag goal = getFlag("green");
                    goalX = goal.getX();
                    goalY = goal.getY();
                }else if(!tank.hasFlag()){
                    goalX = getBase("green").getX();
                    goalY = getBase("green").getY();
                } else {
                    goalX = getBase(color).getX();
                    goalY = getBase(color).getY();
                }
            }else {
                if(!tank.hasFlag() && !havePurpleFlag){
                    Flag goal = getFlag("purple");
                    goalX = goal.getX();
                    goalY = goal.getY();
                }else if(!tank.hasFlag()){
                    goalX = getBase("purple").getX();
                    goalY = getBase("purple").getY();
                } else {
                    goalX = getBase(color).getX();
                    goalY = getBase(color).getY();
                }
            }
            
            
            for(Obstacle obstacle : obstacles) {
                float distance = getObsDistance(obstacle);
                int xSide = 1;
                int ySide = 1;
                
                if(obstacle.getX() > tank.getX()) xSide = -1;
                if(obstacle.getY() > tank.getY()) xSide = -1;
                
                if(distance < 100) {
                    goalY += ySide * obstacle.getY()/10;
                    goalX += xSide * obstacle.getX()/10;
                }
                
                if(distance < 50) {
                    goalX += ySide * obstacle.getY()/5;
                    goalY += xSide * obstacle.getX()/5;
                }
            }

            signed_angle = (float)Math.atan2(y - goalY,x - goalX);

            return (signed_angle - angle);
        }
        
        private float getObsDistance(Obstacle obs) {
            Tank tank = getTank(callsign);
            Point tankLocation = new Point((int)tank.getX(), (int)tank.getY());
            Point obsLocation = new Point((int)obs.getX(), (int)obs.getY());
            
            double distance = tankLocation.distanceSq(obsLocation);
            
            return (float)distance;
            
        }
    }
    
    public void clearTanksWithFlags(){ 
        haveRedFlag = false;
        haveGreenFlag = false;
        havePurpleFlag = false;
    }
    
    public void haveFlag(String color) {
        if(color.equals("red")) haveRedFlag = true;
        if(color.equals("green")) haveGreenFlag = true;
        if(color.equals("purple")) havePurpleFlag = true;
    }
}
