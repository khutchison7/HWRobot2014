/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc1148.modules;

import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc1148.Robot;
import org.usfirst.frc1148.interfaces.RobotModule;

/**
 *
 * @author HW Robotics
 */
public class MotorTestModule implements RobotModule {

    Talon testMotor;
    double speed; //-1 to 1

    public MotorTestModule(Robot aThis) {
    }
    
    public void initModule() {
        testMotor = new Talon(6);
    }

    public void activateModule() {
        testMotor.setSafetyEnabled(false);
    }

    public void deactivateModule() {
        testMotor.setSafetyEnabled(true);
    }

    public void updateTick(int mode) {
        if(mode != 2) return;
        
        testMotor.set(speed);
    }
    
    public void SetSpeed(double speed){
        this.speed = speed;
    }
}
