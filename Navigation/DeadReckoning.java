package Navigation;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;

import EV3.FileAccess;
import EV3.MoveTank;
import EV3.Wait;
import Tools.RunsMenu;

public class DeadReckoning implements Runnable{

	// Members:
	static int width = 75;
	static int height = 92;
	public static final double wheelDiameter = 81.6;
	static int x = 100;
	static int y = 100;
	static double theta = Math.toRadians(0);
	boolean navigate= false;
	public static double vr = 0;
	public static double vl = 10;
	double betha;
	int targetX=0;
	int targetY=0;

	static rectangle robot = new rectangle();

	public static final double time = 1; // In seconds.
	static LinkedList<rectangle> rects = new LinkedList<rectangle>();
	LinkedList<rectangle> obstacles = new LinkedList<rectangle>();
	static LinkedList<Point> targetPoints = new LinkedList<Point>();
	static LinkedList<String> targetPointsNames = new LinkedList<String>();

	public static void calculatePosition() {
		double s = (vr+vl)/2;
		theta = (vr-vl)/width + theta;
		x = (int)(s*Math.cos(theta) + x);
		y = (int)(s*Math.sin(theta) + y);
		robot.setBounds(x-width/2, y-height/2, x+width/2, y+height/2);
	}

	public static void downloadData() {

		read();

		FileAccess f = new FileAccess("obstacles");
		f.delete();
		f.create();

		f.write(rects.size());

		for(int i = 0; i<rects.size(); i++) {
			f.write(rects.get(i).x1);
			f.write(rects.get(i).y1);
			f.write(rects.get(i).x2);
			f.write(rects.get(i).y2);
		}

		f.close();

	}

	public static void saveTragetPoints() {

		try {

			for(int i = 0; i<targetPoints.size(); i++) {

				Formatter file = new Formatter("C:\\Users\\OWNER\\git\\repository\\FLL 2018-2019\\Data\\" + targetPointsNames.get(i));

				file.format("%s", targetPoints.get(i).x + "\r\n");
				file.format("%s", targetPoints.get(i).y + "\r\n");

				file.close();

			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void downloadTargetPoints() {

		for(int i = 0; i<targetPoints.size(); i++) {
			FileAccess file = new FileAccess(targetPointsNames.get(i));
			file.delete();
			file.create();

			file.write(targetPoints.get(i).x);
			file.write(targetPoints.get(i).y);
		}
	}

	public static void read() { 

		File obstacles = new File("C:\\\\Users\\\\OWNER\\\\git\\\\repository\\\\FLL 2018-2019\\\\Data\\\\obstacles");

		try {
			Scanner obstaclesSacn = new Scanner(obstacles);


			while(obstaclesSacn.hasNext()) {
				rects.add(new rectangle(Integer.parseInt(obstaclesSacn.next()), Integer.parseInt(obstaclesSacn.next()),
						Integer.parseInt(obstaclesSacn.next()), Integer.parseInt(obstaclesSacn.next())));
			}

			obstaclesSacn.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public static void setObstacles() {

		FileAccess f = new FileAccess("obstacles");

		int size = (int)f.readNumeric();

		for(int i = 0; i<size; i++) {
			rects.add(new rectangle((int)f.readNumeric(), (int)f.readNumeric(), (int)f.readNumeric(), (int)f.readNumeric()));
		}

		f.close();

	}

	public void measure() {

		double P = (Math.PI*wheelDiameter)/360;
		vl = (MoveTank.leftMotor.getTachoCount()/time)*P*0.5; // pixels per time.
		vr = (MoveTank.rightMotor.getTachoCount()/time)*P*0.5; // pixels per time.

		calculatePosition();

		MoveTank.leftMotor.resetTachoCount();
		MoveTank.rightMotor.resetTachoCount();

	}
	public void startTracking (int startX, int startY) {
		Thread t= new Thread(this); 
		t.start();
	}

	@Override
	public void run() {
		while (RunsMenu.active) {
			if(!navigate) {
				measure();
				Wait.time((int)(time*1000));
			}
			else {
				if (robot.intersects(new Rectangle(targetX, targetY, 1, 1))){ // New intersects
					return;
				}
				for(int i=0; i< obstacles.size(); i++) {
					if (robot.intersects(obstacles.get(i))) {//New intersects.
						
					}
				}
				
				
				Wait.time((int)(time*1000));
				
			}
		}
	}
	public void startNavigation(int targetX, int targetY) {
		
		obstacles = rects;
		
		this.targetX= targetX;
		this.targetY= targetY;

		double alpha = Math.toDegrees(Math.atan((targetX-x) / (targetY-y)));
		betha= alpha+theta;
		Traveler t= new Traveler();
		t.turnInSpot(betha, 100);
		MoveTank.on(100, 100);
		navigate=true;
		
	}
	public void stopNavigation() {
		navigate=false;
	}
	public boolean intersects(rectangle rect1, rectangle rect2) {
		return true;
	}


}

