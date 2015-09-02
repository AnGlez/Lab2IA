import java.util.Random;

import lejos.robotics.subsumption.Behavior;

public class ExploreBehavior implements Behavior{
	
	public static boolean suppressed;
	private static StandardRobot robot;
	private long start;
	
	public ExploreBehavior(StandardRobot r) {
		robot = r;
		start = System.currentTimeMillis();
	}
	
	@Override
	public boolean takeControl() {
		
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		robot.setCurrentBehavior("Avoid");
		
		while (!suppressed) {
			try {
				long current = System.currentTimeMillis();
				Random rand = new Random();
				long sec = rand.nextInt(3) + 4;
				if (current - start > sec *1000){
					StandardRobot.pilot.rotate(rand.nextDouble() * 90 - 45);
					start = current;
					
				}
				
				StandardRobot.pilot.forward();
				Thread.sleep(200);
				
			}catch (Exception ex){}
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
