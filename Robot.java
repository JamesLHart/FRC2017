
package org.usfirst.frc.team3345.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow; // the heck does this do? :) (it was just added this year and I haven't seen any documentation for it yet)
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {
	private Joystick leftJoystick, rightJoystick;
	private Spark leftFront, leftRear, rightFront, rightRear,gearDoor;
	private RobotDrive robotDrive;
	private Talon robotClimber, gearDepositer;
	// please leave this here so that on competition day if the robot is
	// too slow or too fast it can be easily fixed
	private double driveBaseSpeed = 1.0;
	private double gearDepositerSpeed = 0.65;
	private double gearMotorSpeed = 1.0;
	private double gearDoorSpeed = 1.0;

	Timer myTimer;
	
	boolean timerAlreadyStarted = false;
	private boolean timerAlreadStarted;
	private int state = 0;
	
	public final int STATE_FWD = 0;
	public final int STATE_TURN = 1;
	
	@Override
	public void robotInit() {
		// boring initialization stuff here, make sure to change the motor numbers
		// to match the robot
		
		gearDoor = new Spark(0);
		leftFront = new Spark(3);
		leftRear = new Spark(4);
		rightFront = new Spark(1);
		rightRear = new Spark(2);
		robotDrive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
		robotClimber = new Talon(5);
		gearDepositer = new Talon(6);
		leftJoystick = new Joystick(0);
		rightJoystick = new Joystick(1);
	}

	@Override
	public void autonomousInit() {
		// we're using a new temporary Timer object for each delay
		// so no need to initialize
	
		state = 0;
		myTimer = new Timer();
		myTimer.reset();
		myTimer.start();
	}

	@Override
	public void autonomousPeriodic() {
		
		/*if (myTimer.get() < 3.2) {
			robotDrive.drive(0.3, 0.0); // drive forwards 3/10 speed
		} else if (myTimer.get()<5.5){
			robotDrive.drive(0.0,0.0);
		} else if (myTimer.get() < 4.0) {
			gearDepositer.set(-0.65);
		} else if (myTimer.get() < 4.25) {
			//gearDepositer.set(0.65);
			//robotDrive.drive(-0.3, 0.0);
			gearDepositer.set(0.0);
		} else {
			robotDrive.drive(0.0, 0.0);
			gearDepositer.set(0.0);// stop robot
		}*/
		
		if(myTimer.get() < 3.2)
		{
			robotDrive.drive(0.3, 0.0); // drive forwards 3/10 speed
		}
		else
		{
			robotDrive.drive(0.0,0.0);
		}
		
		if(myTimer.get() > 3.5 && myTimer.get() < 3.75)
		{
			gearDepositer.set(-0.65);
		}
		else
		{
			gearDepositer.set(0.0);
		}
	}
		
/**		if (state == STATE_FWD) {
		
			if(myTimer.get() < 5.0) {
			robotDrive.arcadeDrive(1.0,0);
			}else {
				robotDrive.arcadeDrive(0,0);
				state++;
				myTimer.reset();
				myTimer.start();
			}
		}
		 
		if(state == STATE_TURN) {
				
			if(myTimer.get() < 1.0) {
				robotDrive.arcadeDrive(0,-.5);
				}else {
					robotDrive.arcadeDrive(0,0);
					state++;
					myTimer.reset();
					myTimer.start();
				}
		  }
	
		// drive forward for 2 seconds
		/**robotDrive.arcadeDrive(-0.5, 0);
		Timer.delay(2);
		// stop driving forward and move other two motors for half a second
		robotDrive.arcadeDrive(0, 0);
		gearMotor.set(0.5);
		scissorLift.set(0.5);
		Timer.delay(0.5);
		// stop motors
		robotDrive.arcadeDrive(0, 0);
		gearMotor.set(0);
		scissorLift.set(0);*/
		
	   


	@Override
	public void teleopPeriodic() {
		// pass a modified forward/backward speed to the drive train object 
		//robotDrive.arcadeDrive(leftJoystick); Delete it later
		robotDrive.arcadeDrive(-leftJoystick.getY()*driveBaseSpeed, -leftJoystick.getX()*driveBaseSpeed);	
		
		// gear door with modified speed
		if(rightJoystick.getRawButton(6)) {
			gearDoor.set(1*gearMotorSpeed);
		} else if(rightJoystick.getRawButton(7)) {
			gearDoor.set(-1*gearMotorSpeed);
		} else {
			gearDoor.set(0);
		}
		
		// gear motor with modified speed
		if(rightJoystick.getRawButton(2)) {
			robotClimber.set(1*gearMotorSpeed);
		} else if(rightJoystick.getRawButton(3)) {
			robotClimber.set(-1*gearMotorSpeed);
		} else {
			robotClimber.set(0);
		}
		
		// scissor lift with modified speed
		if(rightJoystick.getRawButton(4)) {
			gearDepositer.set(1*gearDepositerSpeed);
		} else if(rightJoystick.getRawButton(5)) {
			gearDepositer.set(-1*gearDepositerSpeed);
		} else {
			gearDepositer.set(0);
		}
	}
	
	@Override
	public void testPeriodic() {
		// we could use this as an alternative to autonomous, but
		// we already have that so this is pointless, for now
	}
}
