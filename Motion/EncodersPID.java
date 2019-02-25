package Motion;

import EV3.Display;
import EV3.MoveTank;
import EV3.Wait;

public class EncodersPID extends Thread{	

	// Members:
	PID pid = new PID();	
	boolean run = true;
	boolean opened = true;
	
	public EncodersPID() {}

	/**
	 * @param target - The PID target
	 */
	public EncodersPID(double target) {
		pid.setTarget(target);
	}

	/**
	 * @param target - The PID target
	 * @param kp - P constant
	 * @param ki - I constant
	 * @param kd - D constant
	 */
	public EncodersPID(double target, double kp, double ki, double kd) {
		pid.setTarget(target);
		pid.setConstants(kp, ki, kd);
	}

	/**
	 * @param target - The PID target
	 */
	public void setTarget(double target) {
		pid.reset();
		pid.setTarget(target);
	}

	/**
	 * @param kp - P constant
	 * @param ki - I constant
	 * @param kd - D constant
	 */
	public void setConstants(double kp, double ki, double kd) {
		pid.setConstants(kp, ki, kd);
	}

	/**
	 * @param baseSpeed - The base speed that the robot drives with (degrees per second).
	 */
	public void setBaseSpeed(double baseSpeed) {
		pid.setBaseSpeed(baseSpeed);
	}

	/**
	 * @param seconds - The time to wait between each calculation.
	 */
	public void setTime(double seconds) {
		pid.setTime(seconds);
	}

	/**
	 * Starts the PID thread and immediately returns.
	 * The robot will start moving until the stopPID will be called.
	 */
	public void startPID() {
		if(run) {
			run = true;
			start();
		} 
		else {
			run = true;
		}
	}

	/**
	 * Stops any PID thread.
	 */
	public void stopPID() {
		run = false;
	}	
	
	public void closePID() {
		opened = false;
	}

	/**
	 * Uses the Gyro sensor to move the robot with the PID calculations.
	 */
	@Override
	public void run() {
		while(run && opened) {
			double turn = pid.calculateTurn(MoveTank.leftMotor.getTachoCount() - MoveTank.rightMotor.getTachoCount()); // Calculates the turn.
			double leftSpeed;
			double rightSpeed;
			// Move depending on a direction.
			if(pid.baseSpeed > 0) { 
				leftSpeed  = pid.baseSpeed - turn;
				rightSpeed = pid.baseSpeed + turn;
			}
			else {
				leftSpeed  = pid.baseSpeed + turn;
				rightSpeed = pid.baseSpeed - turn;
			}
			display(turn, leftSpeed, rightSpeed, MoveTank.leftMotor.getTachoCount() - MoveTank.rightMotor.getTachoCount()); // Not necessary 
			MoveTank.on((int)leftSpeed, (int)rightSpeed); //Move
			Wait.time((int)(pid.time*1000)); // Wait
			while(!run && opened) { // Stop if PID is stoped. 
				Wait.time(100);
			}
		}
	}

	// For debugging...
	private void display(double turn, double leftSpeed, double rightSpeed, double gyro) {
		Display.resetScreen();
		Display.text("Turn: " + turn, 0, 10);
		Display.text("Left: " + leftSpeed, 0, 30);
		Display.text("Right: " + rightSpeed, 0, 50);
		Display.text("Gyro: " + gyro, 0, 70);
	}

}
