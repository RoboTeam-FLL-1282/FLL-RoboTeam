package Motion;

import EV3.MoveTank;

import Tools.Alert;
import Tools.RunsMenu;

public class WhiteLineAlignment {

static double whiteValue = Double.NaN;
	
	/**
	 * @param whiteValue - The value that the sensor returns when it sees the black line.
	 * It can also be any other color value.
	 */
	public static void setWhiteValue(double whiteValue) {
		WhiteLineAlignment.whiteValue = whiteValue;
	}
	
	/**
	 * The robot drives until it recognizes a black line.
	 * Then it aligns on the black line using two color sensors.
	 * @param speed
	 * @param leftPort
	 * @param rightPort
	 */
	public static void align(int speed) {
	
		whiteValue = Aligner.whiteValue;
		if(whiteValue == Double.NaN) {
			Alert.notify("The black value is not set!");
		}
		
		while(Aligner.leftSensor.reflectedLight()<whiteValue && Aligner.rightSensor.reflectedLight()<whiteValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}

		if(Aligner.leftSensor.reflectedLight()>whiteValue) {
			while(Aligner.rightSensor.reflectedLight()<whiteValue && RunsMenu.active) {
				MoveTank.on(speed, 0);
			}
			MoveTank.off();

		}
		else {
			while(Aligner.leftSensor.reflectedLight()<whiteValue && RunsMenu.active) {
				MoveTank.on(0, speed);
			}
			MoveTank.off();
		}
	}
	
	/**
	 * Finds a white line.
	 * @param speed
	 * @return
	 */
	public static Sides find(int speed) {
		
		whiteValue = Aligner.whiteValue;
		
		if(whiteValue == Double.NaN) {
			Alert.notify("The black value is not set!");
		}
		
		while(Aligner.getLeftSensorValue(Colors.BLACK)<whiteValue && Aligner.getRightSensorValue(Colors.BLACK)<whiteValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}
		
		if(Aligner.leftSensor.reflectedLight() >= whiteValue) {
			return Sides.LEFT;
		}
		
		else {
			return Sides.RIGHT;
		}
	}
	
	/**
	 * Finds a white line with only one sensor.
	 * @param side
	 * @param speed
	 */
	public static void find(Sides side, int speed) {

		whiteValue = Aligner.whiteValue;
		
		if(whiteValue == Double.NaN) {
			Alert.notify("The white value is not set!");
		}

		while((side == Sides.LEFT?Aligner.getLeftSensorValue(Colors.BLACK):Aligner.getRightSensorValue(Colors.BLACK))<whiteValue && RunsMenu.active) {
			MoveTank.on(speed, speed);
		}
	}
	
}
