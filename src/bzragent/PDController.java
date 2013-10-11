/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

/**
 *
 * @author cmoncur
 */
public class PDController {
    private static final float KP = 1.0f;
    private static final float KD = .8f;
    
    public static float getAngVel(float errorAngle, float lastErrorAngle) {
        float angvel = ((KP * errorAngle) + KD * calculateDeDt(errorAngle, lastErrorAngle));
        
        if(Math.abs(angvel) < 0.01) return 0;
        
        return angvel;
    }
    
    public static float calculateDeDt(float errorAngle, float lastErrorAngle) {
        return (errorAngle - lastErrorAngle)/Agent.REFRESH_TANKS_INTERVAL;
    }
}
