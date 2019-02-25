package Motion;
import EV3.*;
import lejos.hardware.port.Port;
public class GyroPID extends Thread {

	public static GyroSensor g;

	// Members:
	PID pid = new PID();	
	boolean run = true;
	boolean opened = true;
	
	public GyroPID() {}

	/**
	 * @param target - The PID target
	 */
	public GyroPID(double target) {
		pid.setTarget(target);
	}

	/**
	 * @param target - The PID target
	 * @param kp - P constant
	 * @param ki - I constant
	 * @param kd - D constant
	 */
	public GyroPID(double target, double kp, double ki, double kd) {
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
	 * @param port - The Gyro port (default: port 3).
	 */
	public static void setGyroPort(Port port) {
		g = new GyroSensor(port);
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
	@Override // From Thread.
	public void run() {
		while(run && opened) { // Only run when active.
			double gyroValue = g.angle(); // Take gyro current value.
			double turn = pid.calculateTurn(gyroValue); // Calculates the turn.
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
			
			display(turn, leftSpeed, rightSpeed, gyroValue); // Not necessary 
			
			MoveTank.on((int)leftSpeed, (int)rightSpeed); //Move
			Wait.time((int)(pid.time*1000)); // Wait
			while(!run && opened) { // Wait for the PID to be active again after  
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
