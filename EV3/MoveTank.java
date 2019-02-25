package EV3;
import Motion.Sides;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
public class MoveTank {

	public static EV3LargeRegulatedMotor leftMotor;
	public static EV3LargeRegulatedMotor rightMotor;
	
	/**
	 * Must be called before any other method.
	 * @param leftMotorPort - Left motor's port.
	 * @param rightMotorPort - Right motor's port.
	 */
	public static void setMainMotors(Port leftMotorPort, Port rightMotorPort) {
		leftMotor = new EV3LargeRegulatedMotor(leftMotorPort);
		rightMotor = new EV3LargeRegulatedMotor(rightMotorPort);
	}
	
	/**
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	private static void move(float leftSpeed, float rightSpeed) {
		if(leftSpeed > 0)
			leftMotor.forward();
		else
			leftMotor.backward();
		if(rightSpeed > 0)
			rightMotor.forward();
		else
			rightMotor.backward();
	}
	
	/**
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param rotations
	 * @param brakeAtEnd
	 */
	public static void onForRotations(int leftSpeed, int rightSpeed, int rotations, boolean brakeAtEnd) {
		leftMotor.setSpeed(leftSpeed);
		rightMotor.setSpeed(rightSpeed);
		move(leftSpeed, rightSpeed);
		Delay.msDelay(((rotations*360)/leftSpeed)*1000);
		if(brakeAtEnd) {
			leftMotor.stop(true);
			rightMotor.stop(true);
		}
	}

	/**
	 * Move for specified motor rotations.
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param degrees
	 * @param brakeAtEnd
	 */
	public static void onForDegrees(float leftSpeed, float rightSpeed, double degrees, boolean brakeAtEnd) {
		leftMotor.setSpeed(Math.abs(leftSpeed)); // Set speeds.
		rightMotor.setSpeed(Math.abs(rightSpeed));
		
		// Start moving.
		move(leftSpeed, rightSpeed);
		Delay.msDelay((int)((degrees/Math.abs(rightSpeed))*1000)); // Wait according to speed and rotation.
		
		if(brakeAtEnd) { // Stop moving.
			leftMotor.stop(true);
			rightMotor.stop(true);
		}
	}
	
	/**
	 * Move for specified degrees.
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param degrees
	 * @param brakeAtEnd
	 * @param side
	 */ 
	public static void onForDegrees(float leftSpeed, float rightSpeed, double degrees, boolean brakeAtEnd, Sides side) { // Can be used for moving in arc.
		leftMotor.setSpeed(Math.abs(leftSpeed)); // Set speeds.
		rightMotor.setSpeed(Math.abs(rightSpeed));
		
		// Set the speed for the calculation.
		double speed = (side == Sides.LEFT)?leftSpeed:rightSpeed; 
		
		// Start moving.
		move(leftSpeed, rightSpeed);
		Delay.msDelay((int)((degrees/Math.abs(speed))*1000)); // Wait according to speed and degrees.
		
		if(brakeAtEnd) { // Stop motors.
			leftMotor.stop(true);
			rightMotor.stop(true);
		}
	}

	/**
	 * Move for specified seconds.
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param seconds
	 * @param brakeAtEnd
	 */
	public static void onForSeconds(double leftSpeed, double rightSpeed, double seconds, boolean brakeAtEnd) {
		leftMotor.setSpeed((float)Math.abs(leftSpeed)); // Set speeds.
		rightMotor.setSpeed((float)Math.abs(rightSpeed));
		
		// Start moving.
		move((float)leftSpeed, (float)rightSpeed);
		Delay.msDelay((int)(seconds*1000));
		
		if(brakeAtEnd) { // Stop motors.
			leftMotor.stop(true);
			rightMotor.stop(true);
		}
	}

	/**
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param dis
	 * @param brakeAtEnd
	 */
	public static void onForCent(int leftSpeed, int rightSpeed, double distance, boolean brakeAtEnd) {
		leftMotor.setSpeed(Math.abs(leftSpeed));
		rightMotor.setSpeed(Math.abs(rightSpeed));
		move(leftSpeed, rightSpeed);
		Delay.msDelay((int)((distance/Math.abs(leftSpeed))*1000));
		if(brakeAtEnd) {
			leftMotor.stop(true);
			rightMotor.stop(true);
		}
	}

	/**
	 * Immediately returns.
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public static void on(int leftSpeed, int rightSpeed) {
		leftMotor.setSpeed(Math.abs(leftSpeed));
		rightMotor.setSpeed(Math.abs(rightSpeed));
		move(leftSpeed, rightSpeed);
	}
	
	public static void on(float leftSpeed, float rightSpeed, boolean setSpeed) {
		
		leftMotor.setSpeed(Math.abs(leftSpeed));
		rightMotor.setSpeed(Math.abs(rightSpeed));
		
	}
	
	/**
	 * Stops the motors.
	 */
	public static void off() {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}
	
	/**
	 * Float the motors.
	 */
	public static void flt() {
		leftMotor.flt(true);
		rightMotor.flt(true);
	}

	/**
	 * Use when the motor is no longer needed.
	 */
	public static void close() {
		leftMotor.close();
		rightMotor.close();
	}

}
