package org.usfirst.frc1148.modules;

import org.usfirst.frc1148.Robot;
import org.usfirst.frc1148.data.MoveData;
import org.usfirst.frc1148.interfaces.RobotModule;

import edu.wpi.first.wpilibj.Joystick;

public class JoyStickInputModule implements RobotModule {

    Joystick drive;
    Joystick secControl;
    Joystick thirdControl;
    double driveY;
    double driveX;
    double rotSpeed;
    boolean isResetButtonPressed = false;
    boolean isRelativeTogglePressed = false;
    boolean isLoadButtonPressed = false;
    boolean isLaunchButtonPressed = false;

    MoveData data;
    RobotDriver driver;
    AutoDriveModule autoDrive;
    Robot robot;
    MotorTestModule motorTester;
    
    CatapultModule Catapult;
    CompressorModule Compressor;

    
    public JoyStickInputModule(Robot robot) {
        this.robot = robot;
        data = new MoveData();
    }

    public void initModule() {
        System.out.println("Initializing JoyStickInputModule...");
        this.autoDrive = (AutoDriveModule)robot.GetModuleByName("autodrive");
        this.driver = (RobotDriver)robot.GetModuleByName("robotDriver");
        this.motorTester = (MotorTestModule)robot.GetModuleByName("testmotor");
        
        this.Catapult = (CatapultModule)robot.GetModuleByName("catapult");
        this.Compressor = (CompressorModule)robot.GetModuleByName("compressor");
        
        drive = new Joystick(1);
        secControl = new Joystick(2);
        thirdControl = new Joystick(3);
        System.out.println("JoystickInputModule initialized...");
    }

    public void activateModule()
    {
        //SetMoveData on the driver to establish our dominance
        driver.setMoveData(data);
    }

    public void deactivateModule()
    {
    }

    public void updateTick(int mode) {
        if (mode != 2) { //Manual mode only
            return;
        }
        rotSpeed = drive.getZ()/1.5;
        if (drive.getRawButton(2)) {
            rotSpeed = 0;
        }
        //exponential
        rotSpeed = rotSpeed * Math.abs(rotSpeed);
        driveY = drive.getY() * Math.abs(drive.getY());
        driveX = drive.getX() * Math.abs(drive.getX());

        if (driveY < .01 && driveY > -.01) {
            driveY = 0;
        }
        if (driveX < .01 && driveX > -.01) {
            driveX = 0;
        }
        if (rotSpeed < .01 && rotSpeed > -.01) {
            rotSpeed = 0;
        }
        if (drive.getRawButton(8) && !isResetButtonPressed) { //resets gyro
            driver.resetGyro();
            isResetButtonPressed = true;
            System.out.println("Gyro reset!");
        } else if (!drive.getRawButton(8)) {
            isResetButtonPressed = false;
        }

        if (drive.getRawButton(7) && !isRelativeTogglePressed) { //toggle relative
            driver.ToggleRelative();
            isRelativeTogglePressed = true;
            System.out.println("toggled relative!");
        } else if (!drive.getRawButton(7)) {
            isRelativeTogglePressed = false;
        }
        
        if(drive.getRawButton(11) && !isLoadButtonPressed) //load
        {
            Catapult.Load();
            //Catapult.updateTick(1);
            //wait here
            //Catapult.updateTick(3);
            System.out.println("ball loaded");
        }
        else if (!drive.getRawButton(11)) {
            isLoadButtonPressed = false;
        }
                
        if(drive.getRawButton(9) && !isLaunchButtonPressed) //launch
        {
            Catapult.Launch();
            System.out.println("launched");
        }
        else if (!drive.getRawButton(9)) {
            isLaunchButtonPressed = false;
        }
        

        //Here add your other features
        data.speed = Math.max(Math.abs(drive.getX()), Math.abs(drive.getY()));
        data.angle = drive.getDirectionDegrees();
        data.rotationSpeed = rotSpeed;

        //AUTO ORIENT
        //if (drive.getRawButton(12)) {
        //    autoDrive.OrientTo(45);
        //} else {
        autoDrive.Disable();
        //}

        //Test motor
        //motorTester.SetSpeed(secControl.getTrigger() ? secControl.getY() : 0);
    }
}
