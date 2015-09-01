import lejos.robotics.subsumption.Behavior;

public class ExploreBehavior implements Behavior{
	
	public static boolean suppressed;
	private static StandardRobot robot;
	private float angle;
	
	public ExploreBehavior(StandardRobot r) {
		robot = r;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
