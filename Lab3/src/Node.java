import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Node{

	private HashMap<Node, Integer> neighbors; //neighbor node and edge cost
	private boolean visited;
	private int cost;
	private int id;
	private Node parent;
	
	public Node(int id) {
		neighbors = new HashMap<Node, Integer>();
		this.id = id;
		visited = false;
		cost = 0;
		parent = null;
	}
	public HashMap<Node, Integer> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(HashMap<Node, Integer> neighbors) {
		this.neighbors = neighbors;
	}
	public void setCost(int c) {
		cost = c;
	}
	public int getCost(){
		return cost;
	}
	public void addNeighbor(Node n, int cost) {
		neighbors.put(n, cost); 
	}
	public int getId(){
		return id;
	}
	public boolean isVisited(){
		return visited;
	}
	public void setVisited(boolean v){
		visited = v;
	}
	
	
	public String toString(){
		return id+"";
	}
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
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