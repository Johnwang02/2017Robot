package org.usfirst.frc.team4500.robot.subsystems;

import org.usfirst.frc.team4500.robot.Robot;
import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.commands.OmniDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrainPID extends PIDSubsystem {

	public Talon lOmni, rOmni;
	public Spark fsOmni, bsOmni;
	
	public ADXRS450_Gyro gyro;
	
	public Ultrasonic sonic;
	
	public boolean useSonarInput = true;
	
    public DriveTrainPID() {
    	// Name, P, I, D
    	super("DriveTrainPID", 0.1, 0, 0);
    	lOmni = new Talon(RobotMap.LMOTOR);
    	rOmni = new Talon(RobotMap.RMOTOR);
    	
    	fsOmni = new Spark(RobotMap.FSMOTOR);
    	bsOmni = new Spark(RobotMap.BSMOTOR);
    	
    	gyro = new ADXRS450_Gyro(); 	
    	
    	sonic = new Ultrasonic(8, 9);
    	sonic.setAutomaticMode(true);
    	//getPIDController().
        // Use these to get going:
        //setSetpoint(5);
        //setInputRange(2, 8);//-  Sets where the PID controller should move the system
        //                  to
        //enable(); //- Enables the PID controller.
    }
    
    public void pidMove(double dLow, double dHigh) {
    	SmartDashboard.putNumber("onTarget", getPIDController().getError());
    	enable();
    	setInputRange(dLow, dHigh);
    }
    
    public void moveByDeg(double deg) {
    	enable();
    	setSetpoint(deg);
    }
    
    public boolean getError() {
    	SmartDashboard.putNumber("Error", getPIDController().getError());
    	if(getPIDController().getError() < 6 && getPIDController().getError() > -6) { // 2, -2
    		disable();
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public double getGyroAngle() {
		return gyro.getAngle();
	}
    
    public void resetGyro() {
    	gyro.reset();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new OmniDrive());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	if(useSonarInput == true) {
    		return sonic.getRangeInches();
    	} else {
    		return gyro.getAngle();
    	}
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	if(useSonarInput == true) {
    		if(gyro.getAngle() > 1 || gyro.getAngle() < -1) {
        		if(gyro.getAngle() > 0) {
        			lOmni.set(0.2);
        			rOmni.set(0.2);
        		} else {
        			lOmni.set(-0.2);
        			rOmni.set(-0.2);
        		}
        	} else {
        		lOmni.set(output/3);
            	rOmni.set(-output/3);
        	}
    	} else {
    		Robot.drivetrain.lOmni.set(-output/3);
    		Robot.drivetrain.rOmni.set(-output/3);
    	}
    }
    
    public void omniDrive(double x, double y, double z) {
    	
    	lOmni.set(y);
    	rOmni.set(-y);
    	fsOmni.set(-x+z);
    	bsOmni.set(x+z);
    	
    	/*if(x == 0 && y == 0 && (z > 0.8|| z < -0.8)) {
    		lOmni.set(-z);
    		rOmni.set(-z);
    	} else {
    		lOmni.set(y+z*.5);
        	rOmni.set(-y-z*.5);
        	fsOmni.set(-x+z*.9);
        	bsOmni.set(x+z*.9);*/
    	
    	
    	/*if(joyTwist > 0.3 || joyTwist < -0.3) {
    		fsOmni.set(-joyX);
    		bsOmni.set(joyX);
    		lOmni.set(-joyTwist);
    		rOmni.set(-joyTwist);
    	} else {
    		fsOmni.set(-joyX);
    		bsOmni.set(joyX);
    		lOmni.set(joyY);
    		rOmni.set(-joyY);
    	}*/
    }
    
}



















/*
 * package org.usfirst.frc.team4500.robot.subsystems;

import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.commands.OmniDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrainPID extends PIDSubsystem {

	public Talon lOmni, rOmni;
	public Spark fsOmni, bsOmni;
	
	public ADXRS450_Gyro gyro;
	
	public Ultrasonic sonic;
	
	public boolean useSonarInput = true;
	
    public DriveTrainPID() {
    	// Name, P, I, D
    	super("DriveTrainPID", 0.1, 0, 0);
    	lOmni = new Talon(RobotMap.LMOTOR);
    	rOmni = new Talon(RobotMap.RMOTOR);
    	
    	fsOmni = new Spark(RobotMap.FSMOTOR);
    	bsOmni = new Spark(RobotMap.BSMOTOR);
    	
    	gyro = new ADXRS450_Gyro(); 	
    	
    	sonic = new Ultrasonic(8, 9);
    	sonic.setAutomaticMode(true);
    	//getPIDController().
        // Use these to get going:
        //setSetpoint(5);
        //setInputRange(2, 8);//-  Sets where the PID controller should move the system
        //                  to
        //enable(); //- Enables the PID controller.
    }
    
    public void pidMove(double dLow, double dHigh) {
    	SmartDashboard.putNumber("onTarget", getPIDController().getError());
    	enable();
    	setInputRange(dLow, dHigh);
    }
    
    public void moveByDeg(double deg) {
    	
    }
    
    public boolean getError() {
    	SmartDashboard.putNumber("Error", getPIDController().getError());
    	if(getPIDController().getError() < 6 && getPIDController().getError() > -6) { // 2, -2
    		disable();
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public double getGyroAngle() {
		return gyro.getAngle();
	}
    
    public void resetGyro() {
    	gyro.reset();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new OmniDrive());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return sonic.getRangeInches();
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	if(gyro.getAngle() > 1 || gyro.getAngle() < -1) {
    		if(gyro.getAngle() > 0) {
    			lOmni.set(0.2);
    			rOmni.set(0.2);
    		} else {
    			lOmni.set(-0.2);
    			rOmni.set(-0.2);
    		}
    	} else {
    		lOmni.set(output/3);
        	rOmni.set(-output/3);
    	}
    }
    
    public void omniDrive(double x, double y, double z) {
    	
    	lOmni.set(y);
    	rOmni.set(-y);
    	fsOmni.set(-x+z);
    	bsOmni.set(x+z);
    	
    	if(x == 0 && y == 0 && (z > 0.8|| z < -0.8)) {
    		lOmni.set(-z);
    		rOmni.set(-z);
    	} else {
    		lOmni.set(y+z*.5);
        	rOmni.set(-y-z*.5);
        	fsOmni.set(-x+z*.9);
        	bsOmni.set(x+z*.9);*/
    	
    	
    	/*if(joyTwist > 0.3 || joyTwist < -0.3) {
    		fsOmni.set(-joyX);
    		bsOmni.set(joyX);
    		lOmni.set(-joyTwist);
    		rOmni.set(-joyTwist);
    	} else {
    		fsOmni.set(-joyX);
    		bsOmni.set(joyX);
    		lOmni.set(joyY);
    		rOmni.set(-joyY);
    	}
    }
    
} 
*/
