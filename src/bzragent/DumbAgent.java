/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import static bzragent.Agent.REFRESH_TANKS_INTERVAL;
import bzragent.commands.AngVel;
import bzragent.commands.Shoot;
import bzragent.commands.Speed;
import bzragent.model.Tank;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author cmoncur
 */
public class DumbAgent extends Agent{

    private static final int SHOOT_INTERVAL = 2000;
        
    private ArrayList<TankTimer> tanks;
    
    public DumbAgent(BZRSocket socket) throws IOException{
        super(socket);
        tanks = new ArrayList<TankTimer>();
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
        private Timer moveTimer;
        private Timer turnTimer;
        private String callsign;
        private float angleToGoTo;
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
            
            startMoveTimer();
        }
        
        public void startTurnTimer() {
            lastAngle = getTank(callsign).getAngle();
            angleToGoTo = lastAngle + 1.04719755f;
            float angvel = PDController.getAngVel(angleToGoTo - getTank(callsign).getAngle(), angleToGoTo - lastAngle);
            
            turnTimer = new Timer(REFRESH_TANKS_INTERVAL, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    float angvel = PDController.getAngVel(angleToGoTo - getTank(callsign).getAngle(), angleToGoTo - lastAngle);
                    if(angvel == 0) {
                        setTankAngVel(0);
                        startMoveTimer();
                        turnTimer.stop();
                        return;
                    } else {
                        lastAngle = getTank(callsign).getAngle();
                        setTankAngVel(angvel);
                    }
                    
                    turnTimer.stop();
                    turnTimer = new Timer(REFRESH_TANKS_INTERVAL, this);
                    turnTimer.start();
                }
            });
            
            turnTimer.start();
        }
        
        public void startMoveTimer() {
            moveTimer = new Timer(getNextMoveDelay(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTimer.stop();
                    if(getTank(callsign).getVelocityX() == 0 && getTank(callsign).getVelocityY() == 0){
                        startTank();
                        moveTimer = new Timer(getNextMoveDelay(), this);
                        moveTimer.start();
                    }else{
                        stopTank();
                        startTurnTimer();
                    }
                    
                    
                    
                }
            });
            
            moveTimer.start();
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
            shootTimer.start();
            
            
            try{
                Speed speedCommand = new Speed(getTank(callsign), .5f);
                getSocket().sendCommand(speedCommand);
            }catch(IOException e) {
                
            }
        }
        
        public void stop() {
            shootTimer.stop();
            moveTimer.stop();
            turnTimer.stop();
            try{
                Speed speedCommand = new Speed(getTank(callsign), 0);
                getSocket().sendCommand(speedCommand);
            }catch(IOException e) {
                
            }
        }
        
        private void shoot() {
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
    }

   
    
}
