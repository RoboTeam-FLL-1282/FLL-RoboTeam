 package FLL;

import EV3.MoveTank;
import EV3.Ports;
import Motion.Aligner;
import Motion.GyroPID;
import Tools.Default;
import Tools.RunsMenu;

public class RunsMaster {

	public static void main(String[] args) {
		Default.settings(); //Sets screen and main motors
		Aligner.setSensorsPorts(Ports.S2, Ports.S4); // Sets Aligner sensors.
		GyroPID.setGyroPort(Ports.S3);// Set Gyro port.
		MoveTank.leftMotor.resetTachoCount();
		MoveTank.rightMotor.resetTachoCount();
		
		// Create instances:
		RunsMenu r = new RunsMenu();
		Run_1 r1 = new Run_1(r);
		Run_2_A r2a = new Run_2_A(r);
		Run_2_B r2b = new Run_2_B(r);
		Run_3 r3 = new Run_3(r);
		
		// Add runs to RunsMenu:
		r.addRun(r1, "Run 1", r1.pid);
		r.addRun(r2a, "Run 2 A", r2a.pid);
		r.addRun(r2b, "Run 2 B", r2b.pid);
		r.addRun(r3, "Run 3", r3.pid);
		r.show();
	}
	
}
