package Navigation;

import EV3.BrickButtons;
import EV3.BrickLight;
import EV3.Display;
import EV3.MoveTank;
import EV3.Sound;
import EV3.Wait;
import Motion.Accelerator;
import Motion.BlackLineAlignment;
import Motion.GyroPID;
import Motion.Sides;
import Motion.WhiteLineAlignment;
import Tools.RunsMenu;

public class SpecialFunctions {

	static boolean smiley = true;
	
	/**
	 * Display smiley, sound a beep, and led blinks. 
	 */
	public static void smiley() {
		new Thread() {
			@Override
			public void run() {
				while(smiley && RunsMenu.active) {
					Display.resetScreen();
					BrickLight.on(1);
					Wait.time(500);
					BrickLight.on(0);
					Display.text("   **        **     ", 2, 15);
					Display.text("   **        **     ", 2, 30);
					Display.text("**      *       **  ", 2, 45);
					Display.text(" **            **   ", 2, 60);
					Display.text("  **************    ", 2, 75);
					Display.text("   ************     ", 2, 90);
					Wait.time(500);
				}
			}
		}.start();
	}
	
	/**
	 * Turns smiley off.
	 */
	public static void smileyOff() {
		smiley = false;
	}
	
	/**
	 * Navigates to the far T.
	 * @return
	 */
	public static GyroPID navigateToOpsiteSection() {

		GyroPID pid = new GyroPID(-10, 1, 0.001, 0.001);
		pid.setBaseSpeed(-250);
		smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		smileyOff();
		GyroPID.g.recalibrate();

		// Move robot forwards and align on the white line.
		Accelerator.accelerate(0.5, -150, -250, false);

		Sound.beep(100);
		pid.startPID();
		Wait.time(700);
		pid.stopPID();
		Sound.beep(100);

		
		// Align on white line and then turn x degrees
		WhiteLineAlignment.align(-250);

		Sound.beep(100);
		GyroPID.g.reset();

		//Turn and move to the T.
		Traveler t = new Traveler(0, 0, 12, 8.2);
		t.turnInSpot(70, -100);

		pid.setTarget(GyroPID.g.angle());
		pid.startPID();

		Wait.time(1000);
		pid.stopPID();

		
		// Find line, move forwards and then align backwards.
		BlackLineAlignment.find(Sides.LEFT, -250);

		MoveTank.onForCent(-250, -250, 200, true);

		t.turnInSpot(42, -100);

		BlackLineAlignment.find(-200);

		MoveTank.onForCent(-100, -100, 150, true);

		BlackLineAlignment.align(100);

 
		return pid;
	}
	
	public static GyroPID navigateToFarT() {

		GyroPID pid = new GyroPID(-10, 1, 0.001, 0.001);
		pid.setBaseSpeed(-250);
		smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		smileyOff();
		GyroPID.g.recalibrate();

		// Move robot forwards and align on the white line.
		Accelerator.accelerate(0.5, -150, -250, false);

		Sound.beep(100);
		pid.startPID();

		Wait.time(500);
		pid.stopPID();

		Sound.beep(100);

		// Align on white line and then turn x degrees
		//		Display.resetScreen();
		WhiteLineAlignment.align(-250);

		Sound.beep(100);
		GyroPID.g.reset();
	
		// Turn and move to the T.
		Traveler t = new Traveler(0, 0, 12, 8.2);
		t.turnInSpot(70, -100);

		pid.setTarget(GyroPID.g.angle());
		pid.startPID();

		Wait.time(1000);
		pid.stopPID();

		
		// Find black line, move forwards and align on the line.
		BlackLineAlignment.find(Sides.LEFT, -250);

		MoveTank.onForCent(-250, -250, 200, true);

		t.turnInSpot(42, -100);

		BlackLineAlignment.find(-200);

		MoveTank.onForCent(-100, -100, 100, true);

		BlackLineAlignment.align(100);

 
		return pid;
	}
	
	public static GyroPID getToOppositeSection() {
		GyroPID pid = new GyroPID(-3, 2, 0.001, 0.001);
		pid.setBaseSpeed(-600);
		smiley();
		Sound.beep(100);
		BrickButtons.waitForAnyPress();
		smileyOff();
		GyroPID.g.recalibrate();

		// Move robot forwards and align on the white line.

		Sound.beep(100);
		pid.startPID();
		Wait.time(1500);
		pid.setBaseSpeed(-500);
		Wait.time(800);
		pid.stopPID();
		Sound.beep(100);

		
		Wait.time(500);
		
		// Align on white line and then turn x degrees
		WhiteLineAlignment.find(-400);
		MoveTank.onForCent(-100, -100, 60, true);
		GyroPID.g.reset();
		BlackLineAlignment.find(200);
		WhiteLineAlignment.find(200);
		MoveTank.onForCent(200, 200, 200, false);
		

		Sound.beep(100);

		//Turn and move to the T.
		Traveler t = new Traveler(0, 0, 12, 8.2);
		t.turnInSpot(33, -100); // changed from 35

		pid.setTarget(GyroPID.g.angle());
		pid.setBaseSpeed(-450);
		pid.startPID();

		Wait.time(1000);
		pid.stopPID();
		t.turnInSpot(20, -100);

		
		// Find line, move forwards and then align backwards.
		BlackLineAlignment.find(Sides.LEFT, -150);

		MoveTank.onForCent(-250, -250, 200, true);

		t.turnInSpot(48, -200);

		BlackLineAlignment.find(Sides.LEFT, -200); // Changed

		MoveTank.onForCent(-200, -200, 70, true);

		BlackLineAlignment.align(100);

 
		return pid;
	}
}