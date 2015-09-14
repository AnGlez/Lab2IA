import java.util.LinkedList;
import java.util.Set;

public class UninformedSearch {
	
	public static Graph myGraph;

	//falta: escoger vecino con menos costo primero
	public Node breathFirst(String s, String d){	
		Node start = myGraph.findNode(s);
		Node end = myGraph.findNode(d);
		
		
		LinkedList<Node> frontier = new LinkedList<Node>(); //list of unvisited nodes
		
		System.out.println("BREATH FIRST SEARCH:");
		frontier.add(start); //enqueueing start node to frontier
		int cost = 0; //initial cost is 0
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node shallow = frontier.poll(); //dequeue unvisited node
			System.out.println("Visiting "+shallow.getName()+"...");
			shallow.setVisited(true); //marking node as visited
			if (shallow.equals(end)){ //goal test
				System.out.println("Found destination: "+shallow.getName());
				System.out.println("Total cost: "+cost);
				return shallow;
			}	
			
			for (Node n : shallow.getNeighbors().keySet()){ //check each node's neighbor
				if (!n.isVisited() && shallow.getNeighbors().get(n) != -1){ //enqueue node if hasn't been visited and there is a path to get there
					System.out.println(n.getName()+" hasn't been visited, adding to frontier...");
					n.setCost(n.getCost() + shallow.getNeighbors().get(n)); //setting total cost of getting to node from start node
					cost+=n.getCost();//cumulative cost so far
					System.out.println("Cost so far: "+cost);
					frontier.addLast(n);
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
		Node end = us.breathFirst("Celayork","Leondres");
		
	}
}
