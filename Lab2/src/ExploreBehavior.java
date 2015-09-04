import java.util.Random;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
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
		long current = System.currentTimeMillis();
		Random rand = new Random();
		long sec = rand.nextInt(3) + 2;
		StandardRobot.pilot.forward();
		if (current - start > sec *1000){
			StandardRobot.pilot.rotate(rand.nextDouble() * 90 - 45);
			start = current;
			
		}
		if (StandardRobot.cs.readNormalizedValue() < 300) {
			LCD.clear();
			LCD.drawString("Found a dark spot", 0, 0);
			short [] note = {
					 2349,115, 0,5, 1760,165, 0,35, 1760,28, 0,13, 1976,23,
					 0,18, 1760,18, 0,23, 1568,15, 0,25, 1480,103, 0,18, 1175,180, 0,20, 1760,18,
					 0,23, 1976,20, 0,20, 1760,15, 0,25, 1568,15, 0,25, 2217,98, 0,23, 1760,88,
					 0,33, 1760,75, 0,5, 1760,20, 0,20, 1760,20, 0,20, 1976,18, 0,23, 1760,18,
					 0,23, 2217,225, 0,15, 2217,218}; 
			
			for(int i=0;i<note.length; i+=2) {
				 final short w = note[i+1];
				 final int n = note[i];
				 if (n != 0) Sound.playTone(n, w*10);
				 try { Thread.sleep(w*10); } catch (InterruptedException e) {}
			} 

		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}