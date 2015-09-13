import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node{

	private HashMap<Node, Integer> neighbors; //neighbor node and edge cost
	private String name;
	
	public Node(String name) {
		neighbors = new HashMap<Node, Integer>();
		this.name = name;
	}
	public HashMap<Node, Integer> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(HashMap<Node, Integer> neighbors) {
		this.neighbors = neighbors;
	}	
	public void addNeighbor(Node n, int cost) {
		neighbors.put(n, cost); 
	}
	public String getName(){
		return name;
	}
	public String toString(){
		return "["+name+"]";
	}
	public void print(){
		String node = "";
		Iterator i = neighbors.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry pair = (Map.Entry)i.next();
			node += this.toString()+"-" + pair.getValue() + "->" + pair.getKey().toString()+"\n";
		}
		System.out.println(node);
	}
}
