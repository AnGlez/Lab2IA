import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class StandardRobot {
	
	public static UltrasonicSensor us;
	public static DifferentialPilot df;
	public static ColorSensor cs;
	
	public StandardRobot(){
		us = new UltrasonicSensor(SensorPort.S1);
		cs = new ColorSensor(SensorPort.S2);
		df = new DifferentialPilot(5f,16.5f,Motor.A,Motor.B);
	}
	
}
