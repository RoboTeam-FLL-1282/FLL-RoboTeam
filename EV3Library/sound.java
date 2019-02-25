package EV3Library;
import java.io.File;

import lejos.hardware.*;
public class sound {
	
	/**
	 * Plays a file in a specific path.
	 * @param path
	 * @param volume
	 */
	public static void playSample(String path, int volume) {
		File file = new File(path);
		Sound.playSample(file, volume);
	}
	
	/**
	 * Plays a specific tone according to frequency and volume. 
	 * @param frequency
	 * @param duration
	 * @param volume
	 */
	public static void playTone(int frequency, int duration, int volume) {
		Sound.playTone(frequency, duration, volume);
	}
	
	/**
	 * Sounds a short beep.(Good for debugging).
	 * @param volume
	 */
	public static void beep(int volume) {
		Sound.setVolume(volume);
		Sound.beep();
	}
	
}
