import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class UninformedSearch {
	
	public static void main(String args[]){
		FileReader text;
		Graph myGraph = new Graph();
		
		try {
			text = new FileReader("graph.txt");
			BufferedReader reader = new BufferedReader(text);
			String line;
			int row = 0, col = 0;
			
			//creating nodes
			line = reader.readLine();
			StringTokenizer t = new StringTokenizer(line);
			while (t.hasMoreTokens()){
				myGraph.addNode(new Node(row));
				t.nextToken();
				row++;
			}

			line = reader.readLine();
			row = 0;
			while ((line = reader.readLine()) != null){
				StringTokenizer tk = new StringTokenizer(line);
				col = 0;
				while (tk.hasMoreTokens()){
					Node neigh = myGraph.findNode(col);
					int c = Integer.parseInt(tk.nextToken());
					myGraph.findNode(row).addNeighbor(neigh,c);
					col++;
				}
				row++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		myGraph.print();
	}
}
