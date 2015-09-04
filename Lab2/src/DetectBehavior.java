import java.util.Random;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class DetectBehavior implements Behavior{

	private static StandardRobot robot;
	private boolean suppressed;
	
	public DetectBehavior(StandardRobot sr) {
		robot = sr;
	}
	
	@Override
	public boolean takeControl() {
		return StandardRobot.cs.readNormalizedValue() < 300;
	}

	@Override
	public void action() {
		suppressed = false;
		LCD.clear();
		LCD.drawString("Found a dark spot", 0, 0);
		Sound.buzz();
		Random rand = new Random();
		robot.pilot.rotate(rand.nextDouble() * 180 - 90);
		robot.pilot.travel(-20);
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}
	

}
