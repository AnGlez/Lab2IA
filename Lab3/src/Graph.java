import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class Graph{
	private HashMap<Integer,Node> nodes;
	
	public Graph(){
		nodes = new HashMap<Integer,Node>();
	}
	public void addNode(Node n){
		nodes.put(n.getId(),n);
	}
	/* Reads text file with adjacency matrix 
	 * Creates nodes in first iteration 
	 * Adds nodes to Graph's nodes HashMap
	 * For each node, adds neighbor nodes to Node's neighbor map with their cost*/
	public void load(String path) {
		FileReader text;
		Graph myGraph = this;
		
		try {
			text = new FileReader(path);
			BufferedReader reader = new BufferedReader(text);
			String line;
			int row = 0, col = 0;
			
			line = reader.readLine(); //the first line only tells us how many nodes there are
			StringTokenizer t = new StringTokenizer(line);
			while (t.hasMoreTokens()){ // create each node and add them to the graph
				myGraph.addNode(new Node(row));
				t.nextToken();
				row++;
			}

			line = reader.readLine(); //skipping this line 
			row = 0; //restart the pointer to the first row of the matrix
			while ((line = reader.readLine()) != null){
				StringTokenizer tk = new StringTokenizer(line); //tokenizing current line
				col = 0; //restart pointer to first column of matrix
				while (tk.hasMoreTokens()){
					Node neigh = myGraph.findNode(col); //neighbor of current node
					int c = Integer.parseInt(tk.nextToken());//path cost to neighbor, -1 if there's no path
					myGraph.findNode(row).addNeighbor(neigh,c); //adding node and cost to current node's neighbor map
					col++; //moving pointer to next column of adjacency matrix
				}
				row++;//moving pointer to next row
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
