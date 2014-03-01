/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc1148.modules;

import org.usfirst.frc1148.Robot;
import edu.wpi.first.wpilibj.Compressor;
import org.usfirst.frc1148.interfaces.RobotModule;

/**
 *
 * @author HW Robotics
 */
public class CompressorModule implements RobotModule {

    Compressor comp;
    Robot robot;
    //True for compressor on, false for compressor off
    boolean relayState = true;

    public CompressorModule(Robot aThis) {
        this.robot = aThis;
    }

    public void initModule() {
        System.out.println("Compressor module initialized.");
        //Input 3 for the switch, 9 for the spike relay
        comp = new Compressor(3, 9);
    }

    public void activateModule() {
        System.out.println("Compressor enabled!");
        comp.start();
    }

    public void deactivateModule() {
        System.out.println("Compressor shutdown.");
        comp.stop();
    }

    public void updateTick(int mode) {
        //True if pressurized
        boolean currState = !comp.getPressureSwitchValue();
        if(currState != relayState) {
            relayState = currState;
            if(currState)
            {
                System.out.println("Compressor turned on.");
                activateModule();
            }
            else
            {
                System.out.println("Compressor turned off.");
                deactivateModule();
            }
        }
    }
}
