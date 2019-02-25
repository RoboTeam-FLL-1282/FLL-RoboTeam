package Motion;

import EV3.MoveTank;
import Tools.RunsMenu;

public class LineAlignment {

	/**
	 * Align on Black and White line.
	 * @param speed
	 */
	public static void align(int speed) {

		int turnSpeed = speed-speed/2;

		Sides side = BlackLineAlignment.find(speed);

		turnToBlack(turnSpeed, side);

		side = WhiteLineAlignment.find(speed);

		turnToWhite(turnSpeed, side);
	}

	/**
	 * Align on white and black with only one sensor.
	 * @param side
	 * @param speed
	 */
	public static void align(Sides side, int speed) {

		int turnSpeed = speed-speed/2;

		BlackLineAlignment.find(side, speed);

		turnToBlack(turnSpeed, side);

		side = WhiteLineAlignment.find(speed);

		turnToWhite(turnSpeed, side);
	}


	/**
	 * Aligns on black line.
	 * @param speed
	 */
	public static void alignOnBlack(int speed) {


		int turnSpeed = speed-speed/2;

		Sides side = BlackLineAlignment.find(speed);

		turnToBlack(turnSpeed, side);


	}

	/**
	 * Aligns on white line.
	 * @param speed
	 */
	public static void alignOnWhite(int speed) {


		int turnSpeed = speed-speed/2;

		Sides side = WhiteLineAlignment.find(speed);

		turnToWhite(turnSpeed, side);


	}
	
	/**
	 * Turn on a black line until both of the sensors return the same value.
	 * @param turnSpeed
	 * @param side
	 */
	public static void turnToBlack(int turnSpeed, Sides side) {
		while(Aligner.getLeftSensorValue(Colors.BLACK) != Aligner.getRightSensorValue(Colors.BLACK) 
				|| precision(Aligner.getRightSensorValue(Colors.BLACK), 1) >= 0.6 && RunsMenu.active) {

			if(side == Sides.LEFT) {
				MoveTank.on(turnSpeed, -1*turnSpeed);
			}

			else {
				MoveTank.on(-1*turnSpeed, turnSpeed);
			}

		}

		MoveTank.off();
	}

	/**
	 * Turn on a white line until both of the sensors both of the sensors return the same value.
	 * @param turnSpeed
	 * @param side
	 */
	public static void turnToWhite(int turnSpeed, Sides side) {
		while(Aligner.getLeftSensorValue(Colors.WHITE) != Aligner.getRightSensorValue(Colors.WHITE) 
				/*|| precision(Aligner.rightSensor.reflectedLight(), 1) <= 0.5*/ && RunsMenu.active) {

			if(side == Sides.LEFT) {
				MoveTank.on(turnSpeed, -1*turnSpeed);
			}

			else {
				MoveTank.on(-1*turnSpeed, turnSpeed);
			}

		}

		MoveTank.off();
	}

	/**
	 * Returns a double with a specific precision.
	 * @param number
	 * @param precision
	 * @return
	 */
	public static double precision(double number, int precision) {
		double mul = Math.pow(10, precision);
		return ((int)(number*mul))/mul;
	}

}
