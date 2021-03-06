package Motion;

import EV3.MoveTank;
import Tools.Alert;
import Tools.RunsMenu;

public class BlackLineAlignment {

	static double blackValue = Double.NaN;

	/**
	 * @param blackValue - The value that the sensor returns when it sees the black line.
	 * It can also be any other color value.
	 */
	public static void setBlackValue(double blackValue) {
		BlackLineAlignment.blackValue = blackValue;
	}

	/**
	 * The robot drives until it recognizes a black line.
	 * Then it aligns on the black line using two color sensors.
	 * @param speed
	 * @param leftPort
	 * @param rightPort
	 */
	public static void align(int speed) {

		blackValue = Aligner.blackValue;

		if(blackValue == Double.NaN) {
			Alert.notify("The black value is not set!");
		}

		while(Aligner.leftSensor.reflectedLight()>blackValue && Aligner.rightSensor.reflectedLight()>blackValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}

		if(Aligner.leftSensor.reflectedLight()<blackValue) {
			while(Aligner.rightSensor.reflectedLight()>blackValue && RunsMenu.active) {
				MoveTank.on(speed, 0);
			}
			MoveTank.off();

		}
		else {
			while(Aligner.leftSensor.reflectedLight()>blackValue && RunsMenu.active) {
				MoveTank.on(0, speed);
			}
			MoveTank.off();
		}
	}

	/**
	 * Finds black line.
	 * @param speed
	 * @return
	 */
	public static Sides find(int speed) {

		blackValue = Aligner.blackValue;

		if(blackValue == Double.NaN) {
			Alert.notify("The black value is not set!");
		}

		while(Aligner.getLeftSensorValue(Colors.BLACK)>blackValue && Aligner.getRightSensorValue(Colors.BLACK)>blackValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}

		if(Aligner.getLeftSensorValue(Colors.BLACK) <= blackValue) {
			return Sides.LEFT;
		}

		else {
			return Sides.RIGHT;
		}
	}

	/**
	 * Finds black line with only one sensor.
	 * @param side
	 * @param speed
	 */
	public static void find(Sides side, int speed) {

		blackValue = Aligner.blackValue;		
		
		if(blackValue == Double.NaN) {
			Alert.notify("The black value is not set!");
		}

		while((side == Sides.LEFT?Aligner.getLeftSensorValue(Colors.BLACK):Aligner.getRightSensorValue(Colors.BLACK))>blackValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}
	}
}
