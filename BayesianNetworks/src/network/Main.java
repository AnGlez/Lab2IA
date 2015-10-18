package network;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BayesianNet bNet = new BayesianNet();
		String filepath = new File(".").getAbsolutePath();
		//bNet.printNet();
        filepath = filepath.replace(".", "");
		bNet.load(filepath+"BNnodes.txt");
		bNet.printNet();
		bNet.getCalculator().printQueries();

	}

}
