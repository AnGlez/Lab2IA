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
		//LCD.drawString("Color:"+sr.cs.getColor().getRed() +","+ sr.cs.getColor().getGreen()+","+ sr.cs.getColor().getBlue(), 0, 1);
		LCD.drawString("Current:"+sr.getCurrentBehavior(), 0, 1);
		//try { Thread.sleep(delay); } catch (Exception e) {;}
	}
}
