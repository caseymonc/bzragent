/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import bzragent.commands.Bases;
import bzragent.commands.Flags;
import bzragent.commands.MyTanks;
import bzragent.model.Base;
import bzragent.model.Flag;
import bzragent.model.Tank;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author cmoncur
 */
public abstract class Agent {
    public static final int REFRESH_TANKS_INTERVAL = 800;
    public static final int REFRESH_FLAGS_INTERVAL = 2000;
    
    private final BZRSocket socket;
    private Map<String, Tank> tanks;
    private Map<String, Flag> flags;
    private Map<String, Base> bases;
    private Timer refreshTanksTimer;
    
    public Agent(BZRSocket socket) {
        this.socket = socket;
        tanks = new HashMap<String,Tank>();
        refreshTanks();
        refreshFlags();
        refreshBases();
        
        refreshTanksTimer = new Timer(REFRESH_TANKS_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTanks();
                refreshFlags();
                refreshBases();
            }
        });
        
        refreshTanksTimer.start();
    }
    
    private void refreshTanks() {
        MyTanks tankCommand = new MyTanks();
        try {
            getSocket().sendCommand(tankCommand);
        } catch (IOException ex) {
            Logger.getLogger(DumbAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Tank> serverTanks = tankCommand.getTanks();
        tanks = new HashMap<String,Tank>();
        ArrayList<Tank> tanksWithFlags = new ArrayList<Tank>();
        for(Tank tank : serverTanks) {
            //if(tanks.size() == 1)break;
            tanks.put(tank.getCallsign(), tank);
            if(tank.hasFlag())tanksWithFlags.add(tank);
        }
        
        clearTanksWithFlags();
        for(Tank tank : tanksWithFlags) {
            haveFlag(tank.getFlag());
        }
        
    }
    
    private void refreshFlags() {
        Flags flagsCommand = new Flags();
        
        try {
            socket.sendCommand(flagsCommand);
        } catch (IOException ex) {
            Logger.getLogger(DumbAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Flag> serverFlags = flagsCommand.getFlags();
        flags = new HashMap<String,Flag>();
        for(Flag flag : serverFlags) {
            //if(tanks.size() == 1)break;
            flags.put(flag.getColor(), flag);
        }
    }
    
    private void refreshBases() {
        Bases basesCommand = new Bases();
        
        try {
            socket.sendCommand(basesCommand);
        } catch (IOException ex) {
            Logger.getLogger(DumbAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Base> serverBases = basesCommand.getBases();
        bases = new HashMap<String,Base>();
        for(Base base : serverBases) {
            //if(tanks.size() == 1)break;
            bases.put(base.getColor(), base);
        }
    }
    
    public Base getBase(String color) {
        return bases.get(color);
    }
    
    public Collection<Base> getBases() {
        return bases.values();
    }
    
    public Flag getFlag(String color) {
        return flags.get(color);
    }
    
    public Collection<Flag> getFlags() {
        return flags.values();
    }
    
    public Collection<Tank> getTanks() {
        return tanks.values();
    }
    
    public Tank getTank(String callsign) {
        return tanks.get(callsign);
    }
    
    public BZRSocket getSocket() {
        return socket;
    }
    
    public void haveFlag(String color) {}
    
    public void clearTanksWithFlags(){ }
    
    public abstract void start();
}
