package EV3;
import EV3Library.*;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
public class Display {
	
	private static boolean set = true;
	
	/**
	 * Set the screen before displaying on it.
	 * Should only be called once.
	 */
	public static void setScreen() {
		set = false;
		LCD.clear();
		Delay.msDelay(500);
	}
	
	/**
	 * Displays text on the screen.
	 * @param text
	 * @param x
	 * @param y
	 */
	public static void text(String text, int x, int y) {
		if (set) setScreen(); // Sets the screen in case it wasn't called yet. 
		TextUtils.realDrawString(text, x, y);
	}
	
	/**
	 * Clears the screen.
	 */
	public static void resetScreen() {
		if (set) setScreen(); // Sets the screen in case it wasn't called yet. 
		LCD.clear();
	}
}
