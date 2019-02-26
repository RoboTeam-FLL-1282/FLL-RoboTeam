package FLL;

import EV3.MoveTank;
import Motion.GyroPID;
import Tools.MediumMotors;
import Tools.Run;

public class Boom implements Runnable, MediumMotors{


	Run runnable;
	public GyroPID pid = new GyroPID();
	
	/**
	 * Used for adding a listener. 
	 * @param runnable
	 */
	public Boom(Run runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {
		
		MoveTank.onForCent(-2000, -2000, 5000, false);	
		
	}
	
	
}
