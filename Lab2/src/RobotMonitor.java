import lejos.nxt.LCD;

public class RobotMonitor extends Thread{
	private static int delay;
	private static StandardRobot sr;
	
	public RobotMonitor(int d, StandardRobot s) {
		this.setDaemon(true);
		delay = d;
		sr = s;
	}
	
	public void run(){
		LCD.clear();
		LCD.drawString("Dist:"+StandardRobot.us.getRange(), 0, 0);
		LCD.drawString("Current:"+sr.getCurrentBehavior(), 0, 2);
		//try { Thread.sleep(delay); } catch (Exception e) {;}
	}
}
