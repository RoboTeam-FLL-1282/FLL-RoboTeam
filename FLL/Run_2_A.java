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
		
		SpecialFunctions.smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		SpecialFunctions.smileyOff();

		// Move to the wall
		MoveTank.onForCent(250, 250, 100, true);
		GyroPID.g.recalibrate();		
		
		// Start moving towards M06.
		//pid.setTarget(GyroPID.g.angle());
		pid.startPID();
		Accelerator.acceleratePID(pid, 0.5, -70, -500, false);
		
		Wait.time(2000);
		for(int i = -500; i<0; i+=30) {
			pid.setBaseSpeed(i);
			Wait.time(30);
		}
		pid.stopPID();
		
		
				
		// Align on line:
		WhiteLineAlignment.align(-150);
		MoveTank.off();
		
		BlackLineAlignment.align(-100);
		
		WhiteLineAlignment.align(100);
		
		BlackLineAlignment.align(-100);
		
		WhiteLineAlignment.align(100);
		
		// A little fetch.
		
		MoveTank.onForCent(-70, -70, 120, true);
		// Finds black line and moves backwards.
		
		// Rotates and moves forwards.
		t.turnInSpot(100, -200);
		
		BlackLineAlignment.find(Sides.RIGHT, -100);
		
		MoveTank.onForCent(-100, -100, 90, true);
		
		
		// Align in line.
		WhiteLineAlignment.align(100);
		
		BlackLineAlignment.align(100);
		
		WhiteLineAlignment.align(-100);
		
		BlackLineAlignment.align(100);
		
		// Move to collect the resources.
		MoveTank.onForCent(-100, -100, 105, true); // -100
		
		
		// A little turn.
		t.turnInSpot(110, 70);
		
		MoveTank.onForCent(300, 300, 520, true); // 100
		
		Wait.time(500);
		
		
		// Move towards M06
		MoveTank.onForCent(-250, -250, 480, true);
		
		
		// Turn and move towards the wall
		t.turnInSpot(125, -200); // Sure?
		
		

		MoveTank.onForCent(-300, -300, 100, true);

		
		// Align
		WhiteLineAlignment.align(150);
				
		t.turnInSpot(15, 200);
				
		
		// Move and release the big wheels
		MoveTank.onForCent(-150, -150, 410, true);
		
		

		t.turnInSpot(102, 100);

		
		// Move backwards
		MoveTank.onForCent(300, 300, 440, true);
		
		

		MoveTank.onForCent(-200, -200, 90, true);

		
		// Rotate medium motor and move.
		c.onForSeconds(200, 0.5, true);
		

		MoveTank.onForCent(-250, -250, 60, true);

		

		// Move to throw the meteorids.
		t.turnInSpot(25, -100);
		
		MoveTank.onForCent(-250, -250, 370, true);
		
		t.turnInSpot(25, 100);
		
		MoveTank.onForCent(-250, -250, 290, true);
		
		t.turnInSpot(16, 100);
		
		
		// Throw balls.
		b.onForSeconds(1600, 0.23, true);
		
		MoveTank.onForCent(200, 200, 350, true);
		
		b.onForSeconds(1000, 0.7, true);

		MoveTank.onForCent(-200, -200, 200, true);
		
		t.turnInSpot(5, 100);
		
		b.onForSeconds(-3000, 2, true);

		

		// Coming back to base.

		t.turnInSpot(67, -300);

		

		MoveTank.onForCent(900, 900, 850, true);

		

		t.turnInSpot(75, 300);

		

		MoveTank.onForCent(900, 900, 1600, true);

		

		runnable.runFinished();
	}
	
	
}
