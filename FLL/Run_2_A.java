package FLL;

import EV3.BrickButtons;
import EV3.MoveTank;
import EV3.Sound;
import EV3.Wait;
import Motion.Accelerator;
import Motion.BlackLineAlignment;
import Motion.GyroPID;
import Motion.Sides;
import Motion.WhiteLineAlignment;
import Navigation.SpecialFunctions;
import Navigation.Traveler;
import Tools.MediumMotors;
import Tools.Run;
import Tools.RunsMenu;

public class Run_2_A implements Runnable, MediumMotors{


	Run runnable;
	public GyroPID pid = new GyroPID();
	
	/**
	 * Used for adding a listener. 
	 * @param runnable
	 */
	public Run_2_A(Run runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {
		
		Traveler t = new Traveler(0, 0, 12, 8.2);
		
		pid = new GyroPID(0, 0.3, 0.05, 0.1);
		pid.setBaseSpeed(-200);
		
		if(!RunsMenu.active) return; //Break point

		SpecialFunctions.smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		SpecialFunctions.smileyOff();

		MoveTank.onForCent(250, 250, 100, true);
		GyroPID.g.recalibrate();		
		
		// Start moving towards M06.
		//pid.setTarget(GyroPID.g.angle());
		pid.startPID();
		Accelerator.acceleratePID(pid, 0.5, -70, -500, false);
		if(!RunsMenu.active) return; // Break point
		Wait.time(2000);
		for(int i = -500; i<0; i+=30) {
			pid.setBaseSpeed(i);
			Wait.time(30);
		}
		pid.stopPID();
		
		if(!RunsMenu.active) return; // Break point
				
		// Align on line:
		WhiteLineAlignment.align(-150);
		MoveTank.off();
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(-100);
		if(!RunsMenu.active) return; // Break point
		WhiteLineAlignment.align(100);
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(-100);
		if(!RunsMenu.active) return; // Break point
		WhiteLineAlignment.align(100);
		
		// A little fetch.
		if(!RunsMenu.active) return; // Break point
		MoveTank.onForCent(-70, -70, 150, true);
		// Finds black line and moves backwards.
		
		// Rotates and moves forwards.
		t.turnInSpot(100, -200);
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.find(Sides.LEFT, -100);
		if(!RunsMenu.active) return; // Break point
		MoveTank.onForCent(-100, -100, 90, true);
		if(!RunsMenu.active) return; // Break point
		
		// Align in line.
		WhiteLineAlignment.align(100);
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(100);
		if(!RunsMenu.active) return; // Break point
		WhiteLineAlignment.align(-100);
		if(!RunsMenu.active) return; // Break point
		BlackLineAlignment.align(100);
		
		// Move to collect the resources.
		MoveTank.onForCent(-100, -100, 105, true); // -100
		if(!RunsMenu.active) return; // Break point
		
		// A little turn.
		t.turnInSpot(110, 70);
		
		// Coming back to base.
		MoveTank.onForCent(300, 300, 590, true); // 100
		
		if(!RunsMenu.active) return; // Break point
		
		MoveTank.onForCent(-250, -250, 600, true);
		
		if(!RunsMenu.active) return; // Break point
		
		t.turnInSpot(130, -200);
		
		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(-300, -300, 100, true);

		if(!RunsMenu.active) return; // Break point
		
		WhiteLineAlignment.align(150);
		
		t.turnInSpot(15, 200);
				
		if(!RunsMenu.active) return; // Break point
		
		MoveTank.onForCent(-150, -150, 395, true);
		
		if(!RunsMenu.active) return; // Break point

		t.turnInSpot(95, 150);

		if(!RunsMenu.active) return; // Break point
		
		MoveTank.onForCent(300, 300, 440, true);
		
		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(-200, -200, 100, true);

		if(!RunsMenu.active) return; // Break point

		c.onForSeconds(1000, 1.5, true);
		
		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(-500, -500, 200, true);

		if(!RunsMenu.active) return; // Break point

		t.turnInSpot(23, 400);

		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(-2000, -2000, 350, true);

		if(!RunsMenu.active) return; // Break point

		t.turnInSpot(110, -300);

		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(900, 900, 800, true);

		if(!RunsMenu.active) return; // Break point

		t.turnInSpot(80, 300);

		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(900, 900, 1400, true);

		if(!RunsMenu.active) return; // Break point

		runnable.runFinished();
	}
	
	
}
