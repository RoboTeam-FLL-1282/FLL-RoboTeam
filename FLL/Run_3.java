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
import Tools.RunsMenu;

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
		if(!RunsMenu.active) return; // Break point
		
		Sound.beep(100);
		b.onForDegrees(-300, 300, true);
		if(!RunsMenu.active) return; // Break point
		
		// Aligns on line and approaches M013.
		BlackLineAlignment.align(100);
		WhiteLineAlignment.align(-100);
		BlackLineAlignment.align(100);
		if(!RunsMenu.active) return; // Break point
		
		MoveTank.onForCent(100, 100, 50, true);
		if(!RunsMenu.active) return; // Break point
		
		t.turnInSpot(90, -100);
		if(!RunsMenu.active) return; // Break point
		
		BlackLineAlignment.align(200);
		WhiteLineAlignment.align(100);
		BlackLineAlignment.align(-100);
		WhiteLineAlignment.align(100);
		BlackLineAlignment.align(-100);
		MoveTank.onForCent(100, 100, 160, false);
		
		t.turnInSpot(55, -200);
		double target = GyroPID.g.angle();
//		pid.setTarget(-230);
		pid.setTarget(target);
		pid.setBaseSpeed(250);
		pid.startPID();
		Wait.time(1500);
		pid.stopPID();
		Sound.beep(200);
		MoveTank.onForCent(-100, 100, 200, true);
		MoveTank.onForCent(-100, -100, 50, true);
		MoveTank.onForCent(100, -100, 200, true);
		Sound.beep(200);
		pid.setTarget(target);
		pid.setConstants(3, 0.05, 0.5);
		pid.startPID();
		Wait.time(3500);
		pid.stopPID();
		if(!RunsMenu.active) return; // Break point
				
		Wait.time(500);
				
		MoveTank.onForCent(-200, -200, 350, true);
				
		t.turnInSpot(150, 200);
		MoveTank.onForCent(-250, -250, 270, true);
				
		MoveTank.onForCent(250, 250, 250, true);
		MoveTank.onForCent(-250, -250, 70, true);
		t.turnInSpot(60, -200);
		
		if(!RunsMenu.active) return; // Break point
		
		MoveTank.onForCent(200, 200, 30, true);
		
		if(!RunsMenu.active) return; // Break point

		t.turnInSpot(60, -200);
		
		c.onForSeconds(-3000, 6, true);
		
		t.turnInSpot(10, -200);
				
		if(!RunsMenu.active) return; // Break point

		c.on(50000);
		if(!RunsMenu.active) return; // Break point

		MoveTank.onForCent(-200, -200, 260, false);
				
		// Coming back to space.
		MoveTank.onForCent(-700, -700, 350, true);
		if(!RunsMenu.active) return; // Break point
		t.turnInSpot(100, 400);
		if(!RunsMenu.active) return; // Break point
		MoveTank.onForCent(-700, -700, 850, false);
	
		if(!RunsMenu.active) return; // Break point
		t.turnInSpot(100, -400);
		if(!RunsMenu.active) return; // Break point
		MoveTank.onForCent(-700, -700, 700, false);
			
		if(!RunsMenu.active) return; // Break point
		t.turnInSpot(50, 400);
		if(!RunsMenu.active) return; // Break point
		MoveTank.onForCent(-700, -700, 1200, true);
		
		c.off();
		
		if(!RunsMenu.active) return; // Break point
//				
//		t.turnInSpot(20, 100);
//		if(!RunsMenu.active) return; // Break point
//		
//		MoveTank.onForCent(-900, -900, 1000, true);
//		if(!RunsMenu.active) return; // Break point
//		t.turnInSpot(30, 100);
//		MoveTank.onForCent(-900, -900, 700, true);
	
		pid.closePID();
		runnable.runFinished();
	}
	
}