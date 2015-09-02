import java.util.Random;

import lejos.robotics.subsumption.Behavior;

public class AvoidBehavior implements Behavior{
	private static StandardRobot robot;
	private int safeDistance;
	private boolean suppressed;
	
	public AvoidBehavior(StandardRobot s, int sd) {
		robot = s;
		safeDistance = sd;
	}
	
	@Override
	public boolean takeControl() {
		
		return StandardRobot.us.getRange() < safeDistance;
		
	}

	@Override
	public void action() {
		suppressed = false;
		
		while (!suppressed) {
			robot.setCurrentBehavior("Avoid");
			Random rand = new Random();
			double range = 180;
			StandardRobot.pilot.rotate((rand.nextDouble() * range - 90)* -1);
			StandardRobot.pilot.travel(rand.nextInt(30));
			/*try {
				Thread.sleep(200);
			}catch (Exception ex){;}*/
			//StandardRobot.pilot.stop();
		}		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
