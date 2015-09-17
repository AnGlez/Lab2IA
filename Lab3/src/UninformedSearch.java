import java.util.LinkedList;

public class UninformedSearch {
	
	public static Graph myGraph;

	public Node breathFirst(int s, int d){	
		Node start = myGraph.findNode(s);
		Node end = myGraph.findNode(d);
		LinkedList<Node> frontier = new LinkedList<Node>(); //list of unvisited nodes
		
		System.out.println("BREATH FIRST SEARCH:");
		frontier.add(start); //enqueueing start node to frontier
		int cost = 0; //initial cost is 0
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node shallow = frontier.poll(); //dequeue unvisited node
			System.out.println("Visiting node "+shallow.getId()+"...");
			shallow.setVisited(true); //marking node as visited
			if (shallow.equals(end)){ //goal test
				System.out.println("Found destination: "+shallow.getId());
				//System.out.println("Total cost: "+cost);
				return shallow;
			}	
			boolean inFrontier[] = new boolean[shallow.getNeighbors().size()];
			for (int i = 0; i < inFrontier.length; i++) {
				inFrontier[i] = false;
			}
			
			for (Node n : shallow.getNeighbors().keySet()){ //check each node's neighbor
				if (!n.isVisited() && shallow.getNeighbors().get(n) != -1 && !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
					System.out.println(n.getId()+" hasn't been visited, adding to frontier...");
					frontier.addLast(n);
					inFrontier[n.getId()] = true;
					System.out.println("Frontier: "+frontier.toString());
				}
			}
		}
		System.out.println("Destination not found :(");
		return null;
	}
	
	public Node depthFirst(String start, String destination){
		
		return null;
	}
	
	public Node uniformCost(String start, String destination){
		return null;
	}
	
	public static void main(String args[]){
		myGraph = new Graph();
		myGraph.load("graph.txt");
		//myGraph.print();
		UninformedSearch us = new UninformedSearch();
		Node end = us.breathFirst(0,2);
		
	}
}
