package FLL;


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

public class Run_3 implements Runnable, MediumMotors{
	
	Run runnable;
	public GyroPID pid = new GyroPID();
	
	/**
	 * Used for adding a listener. 
	 * @param runnable
	 */
	public Run_3(Run runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {		
		
		pid = SpecialFunctions.getToOppositeSection(); 	// Navigates to the far T. Replaced from navigateToOpsiteSection

		pid.setConstants(3, 0.05, 0.5);
		
		Traveler t = new Traveler(0, 0, 12, 8.2);
		
		// Moving towards the food production.
		MoveTank.onForCent(-70, -70, 300, true);
		
		
		Sound.beep(100);
		b.onForDegrees(-300, 300, true);
		
		
		// Aligns on line and approaches M013.
		BlackLineAlignment.align(100);
		WhiteLineAlignment.align(-100);
		BlackLineAlignment.align(100);
		
		
		MoveTank.onForCent(100, 100, 50, true);
		
		
		t.turnInSpot(90, -100);
		
		// Align
		BlackLineAlignment.align(200);
		WhiteLineAlignment.align(100);
		BlackLineAlignment.align(-100);
		WhiteLineAlignment.align(100);
		BlackLineAlignment.align(-100);
		MoveTank.onForCent(100, 100, 160, false);
		
		// Turn
		t.turnInSpot(55, -200);
		double target = GyroPID.g.angle();
		
		// Move to the T
		pid.setTarget(target);
		pid.setBaseSpeed(250);
		pid.startPID();
		Wait.time(1500);
		pid.stopPID();
		
		// Positioning...
		Sound.beep(200);
		MoveTank.onForCent(-100, 100, 200, true);
		MoveTank.onForCent(-100, -100, 50, true);
		MoveTank.onForCent(100, -100, 200, true);
		
		Sound.beep(200);
		
		// Move to take lander.
		pid.setTarget(target);
		pid.setConstants(3, 0.05, 0.5);
		pid.startPID();
		Wait.time(3500);
		pid.stopPID();
		
				
		Wait.time(500);
				
		// Push solar panel.
		MoveTank.onForCent(-200, -200, 350, true);
				
		t.turnInSpot(150, 200);
		MoveTank.onForCent(-250, -250, 270, true);
				
		MoveTank.onForCent(250, 250, 250, true);
		MoveTank.onForCent(-250, -250, 70, true);
		t.turnInSpot(120, -200);
		
		
		
		MoveTank.onForCent(200, 200, 50, true);
		
		
		
		// Put satellites.
		c.onForSeconds(-3000, 6, true);
		
		t.turnInSpot(10, -200);
				

		c.on(50000);
		

		MoveTank.onForCent(-200, -200, 260, false);
				
		// Coming back to base.
		MoveTank.onForCent(-700, -700, 400, true);
		
		t.turnInSpot(100, 400);
		
		MoveTank.onForCent(-700, -700, 800, false);
	
		
		t.turnInSpot(100, -400);
		
		MoveTank.onForCent(-700, -700, 700, false);
			
		
		t.turnInSpot(50, 400);
		
		MoveTank.onForCent(-700, -700, 1200, true);
		
		c.off();
	
		pid.closePID();
		runnable.runFinished();
	}
	
}