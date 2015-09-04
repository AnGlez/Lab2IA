import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class StandardRobot {
	
	public static UltrasonicSensor us;
	public static DifferentialPilot pilot;
	public static LightSensor cs;
	public String currentBehavior;
	public static int safeDistance;
	
	public String getCurrentBehavior() {
		return currentBehavior;
	}

	public void setCurrentBehavior(String currentBehavior) {
		this.currentBehavior = currentBehavior;
	}

	public StandardRobot(){
		us = new UltrasonicSensor(SensorPort.S1);
		cs = new LightSensor(SensorPort.S3);
		pilot = new DifferentialPilot(5f,16.5f,Motor.A,Motor.B);
		currentBehavior = "IDLE";
		safeDistance = 50;
		
	}
	public int getSafeDistance(){
		return safeDistance;
	}
	public static void main(String args[]){
		StandardRobot me = new StandardRobot();
		AvoidBehavior avoid = new AvoidBehavior(me);
		ExploreBehavior exp = new ExploreBehavior(me);
		DetectBehavior det = new DetectBehavior(me);
		Behavior [] bArray = {exp,det,avoid};
		Arbitrator arb = new Arbitrator(bArray);
		Button.waitForAnyPress();
		
		arb.start();
	}
	
}
