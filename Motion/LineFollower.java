package Motion;

import EV3.ColorSensor;
import EV3.Display;
import EV3.MoveTank;
import EV3.Wait;
import Tools.RunsMenu;

public class LineFollower implements Runnable {

	boolean run = true;
	boolean open = true;
	
	ColorSensor sensor = Aligner.leftSensor;

	public PID pid = new PID();
	
	public LineFollower(double kp) {
		pid.kp = kp;
		sensor.setReflectedLightMode();
	}
	
	public void setSensor(Sides side) {
		if(side == Sides.LEFT)
			sensor = Aligner.leftSensor;
		else
			sensor = Aligner.rightSensor;
		sensor.setReflectedLightMode();
	}
	
	public void startLineFollowing() {
		if(run) {
			Thread t = new Thread(this);
			t.start();
			return;
		}
		run = true;
			
	}
	
	public void stopLineFollowing() {
		run = false;
		MoveTank.off();
	}
	
	public void closeLineFollower() {
		open = false;
	}
	
	public void setTarget(double target) {
		pid.setTarget(target);
		pid.reset();
	}
	
	@Override
	public void run() {
		while(run && open && RunsMenu.active) {
			
			pid.calculateError(sensor.reflectedLight());
			
			double turn = pid.P();
			
			double leftSpeed;
			double rightSpeed;
			
			// Move depending on a direction.
			if(pid.baseSpeed > 0) { 
				leftSpeed  = pid.baseSpeed - turn;
				rightSpeed = pid.baseSpeed + turn;
			}
			else {
				leftSpeed  = pid.baseSpeed + turn;
				rightSpeed = pid.baseSpeed - turn;
			}
			
			MoveTank.on((int)leftSpeed, (int)rightSpeed); //Move
			
			Display.text(turn + "", 0, 0);
			
			Wait.time((int)(pid.time*1000)); // Wait
			
			while((!run && open) && RunsMenu.active) Wait.time(100);
		}
	}	
}
