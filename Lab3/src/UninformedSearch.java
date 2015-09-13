import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class UninformedSearch {
		
	public static void main(String args[]){
		Graph myGraph = new Graph();
		myGraph.load("graph.txt");
		myGraph.print();
		
	}
}
