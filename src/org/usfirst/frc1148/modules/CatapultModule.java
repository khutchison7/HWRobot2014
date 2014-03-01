package org.usfirst.frc1148.modules;

import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc1148.Robot;
import org.usfirst.frc1148.interfaces.RobotModule;
import edu.wpi.first.wpilibj.Timer;

public class CatapultModule implements RobotModule {
    Robot robot;
    Solenoid sol;

    /*
     * States List
     *    0: Unloaded
     *    1: Loading
     *    2: unloading
     *    3: Loaded
     * */
    int state = 0;

    /*
     * We process the target state when NOT in the mid position.
     * */
    int targetState = 0;

    public CatapultModule(Robot robot) {
        this.robot = robot;
    }

    public void initModule() {
        System.out.println("Initialzing catapult module!");
        sol = new Solenoid(1);
        System.out.println("Catapult module initialized.");
    }

    public void activateModule() {
        System.out.println("Catapult module activated!");
    }

    public void deactivateModule() {
        System.out.println("Catapult module safely deactivating!");
        
        System.out.println("Catapult module deactivated!");
    }

    public void Launch()
    {
        targetState = 0;
    }

    public void Load() {
        targetState = 3;
    }

    public void updateTick(int mode) {
        switch (state) {
        case 0:
            //do nothing
            break;
        case 1:
            //Loading, send the signal to the solenoid
            if(sol.get())
                state=3;
            sol.set(true);
            break;
        case 2:
            //Unloading, send the signal to the solenoid
            if(!sol.get())
                state = 0;
            sol.set(false);
            break;
        case 3:
            //Loaded, do nothing
            break;
        }

        if(targetState != state && (state == 0 || state == 3)) {
            state += targetState > state ? 1 : -1;
        }
    }
}
