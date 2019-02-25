package Tools;

import EV3.BrickButtons;
import EV3.Buttons;
import EV3.ColorSensor;
import EV3.GyroSensor;
import EV3.Ports;
import EV3.UltraSonicSensor;
import EV3.Wait;
import Motion.Aligner;
import Motion.Colors;

public class PortView implements Runnable{
	
	static boolean run = true;
	
	// Create sensors instances.
	static UltraSonicSensor u = new UltraSonicSensor(Ports.S1);
	static ColorSensor c1 = new ColorSensor(Ports.S2);
	static GyroSensor g = new GyroSensor(Ports.S3);
	static ColorSensor c2 = new ColorSensor(Ports.S4);
	
	public static void main(String[] args) {
		
		// Set sensor modes and start displaying the UI.
		c1.setReflectedLightMode();
		c2.setReflectedLightMode();
		Aligner.setSensorsPorts(Ports.S2, Ports.S4);

		Thread t = new Thread(new PortView());
		t.start();
		
		BrickButtons.waitForPress(Buttons.ESCAPE);
		
		run = false;
		
	}
	
	@Override
	public void run() {
		while(run) {
			// Displaying the the sensor's values.
			Alert.deleteAll();
			Alert.view("1. US", u.distance());
			Alert.view("2. Color", Aligner.getLeftSensorValue(Colors.WHITE));
			Alert.view("3. Gyro", g.angle());
			Alert.view("4. Color", Aligner.getRightSensorValue(Colors.WHITE));
			Wait.time(100);
		}
	}
	
}
