import java.util.Random;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class AvoidBehavior implements Behavior{
	private static StandardRobot robot;
	private boolean suppressed;
	
	public AvoidBehavior(StandardRobot s) {
		robot = s;
	}
	public boolean obstacleDetected(){
		return robot.us.getDistance() < robot.getSafeDistance();
	}
	@Override
	public boolean takeControl() {
		
		return robot.us.getDistance() < robot.getSafeDistance();
		
	}

	@Override
	public void action() {
		suppressed = false;
		Sound.beep();
		robot.setCurrentBehavior("Avoid");
		Random rand = new Random();
		double range = 180;
		while (obstacleDetected()) {
			StandardRobot.pilot.rotate((rand.nextDouble() * range - 90)* -1);
		}
		
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
