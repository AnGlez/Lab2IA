import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Graph{
	private HashMap<Integer,Node> nodes;
	
	public Graph(){
		nodes = new HashMap<Integer,Node>();
	}
	public void addNode(Node n){
		nodes.put(n.getId(),n);
	}
	public void print(){
		Iterator i = nodes.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry pair = (Map.Entry)i.next();
			Node n = (Node) pair.getValue();
			n.print();
		}
		
	}
	public Node findNode(int id) {
		return nodes.get(id);
	}
}
