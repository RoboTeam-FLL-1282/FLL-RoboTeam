package FLL;

import EV3.BrickButtons;
import EV3.MoveTank;
import EV3.Sound;
import EV3.Wait;

import Motion.BlackLineAlignment;
import Motion.GyroPID;
import Motion.WhiteLineAlignment;
import Navigation.SpecialFunctions;
import Navigation.Traveler;
import Tools.MediumMotors;
import Tools.Run;
import Tools.RunsMenu;

public class Run_2_B implements Runnable, MediumMotors{


	Run runnable;
	public GyroPID pid = new GyroPID();
	
	/**
	 * Used for adding a listener. 
	 * @param runnable
	 */
	public Run_2_B(Run runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {
		
		
		Traveler t = new Traveler(0, 0, 12, 8.2);
		
		pid = new GyroPID(0, 20, 0.05, 0.5);
		pid.setBaseSpeed(-500);
		
		if(!RunsMenu.active) return; //Break point

		SpecialFunctions.smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		SpecialFunctions.smileyOff();

		GyroPID.g.recalibrate();		
		MoveTank.onForCent(250, 250, 20, true);
		
		// Start moving towards M06.
		c.on(-15000);
		pid.setTarget(GyroPID.g.angle());
		pid.setBaseSpeed(-250);
		pid.startPID();
		if(!RunsMenu.active) return; // Break point
		Wait.time(4000);
		for(int i = -500; i<0; i+=60) {
			pid.setBaseSpeed(i);
			Wait.time(100);
		}
		pid.stopPID();
		
		if(!RunsMenu.active) return; // Break point
		
		t.turnInSpot(20, -100);
		
		// Align on line:
		WhiteLineAlignment.align(-100);
		MoveTank.off();
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(-100);
		if(!RunsMenu.active) return; // Break point
		WhiteLineAlignment.align(100);
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(-100);
		if(!RunsMenu.active) return; // Break point
		
		// A little fetch.
		MoveTank.onForCent(-100, -100, 90, true);		
		if(!RunsMenu.active) return; // Break point
		
		c.onForSeconds(-1000, 5, true);
		c.onForSeconds(5000, 6, true);
		Wait.time(500);
		c.onForSeconds(-4000, 3, true);

		
	}
	
	
}
