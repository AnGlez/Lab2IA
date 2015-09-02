import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class StandardRobot {
	
	public static UltrasonicSensor us;
	public static DifferentialPilot pilot;
	public static ColorSensor cs;
	public String currentBehavior;
	
	public String getCurrentBehavior() {
		return currentBehavior;
	}

	public void setCurrentBehavior(String currentBehavior) {
		this.currentBehavior = currentBehavior;
	}

	public StandardRobot(){
		us = new UltrasonicSensor(SensorPort.S1);
		cs = new ColorSensor(SensorPort.S2);
		pilot = new DifferentialPilot(5f,16.5f,Motor.A,Motor.B);
		
	}
	
	public static void main(String args[]){
		StandardRobot me = new StandardRobot();
		AvoidBehavior avoid = new AvoidBehavior(me, 50);
		ExploreBehavior exp = new ExploreBehavior(me);
		RobotMonitor monitor = new RobotMonitor(100,me);
		monitor.start();
		
		Behavior [] bArray = {exp,avoid};
		Arbitrator arb = new Arbitrator(bArray);
		Button.waitForAnyPress();
		
		arb.start();
	}
	
}
