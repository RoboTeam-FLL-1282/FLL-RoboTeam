package Tools;

import EV3.BrickButtons;
import EV3.Buttons;

public class Brick extends Thread {

	public boolean run = true;
	BrickButtonsListener object;
	Buttons button;

	/**
	 * @param object
	 */
	public Brick(BrickButtonsListener object, Buttons button) {
		this.object = object;
		this.button = button;
		start();
	}
	
	/**
	 *  Stops the listener.
	 */
	public void closeListener() {
		run = false;
	}
	
	@Override
	public void run() {
		while(run) {
			BrickButtons.waitForPress(button);
			object.onPress(button);
		}
	}
	
}
