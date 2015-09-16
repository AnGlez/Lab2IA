import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class UninformedSearch {
	
	public static Graph myGraph;

	public Node breadthFirst(String s, String d){	
		Node start = myGraph.findNode(s);
		Node end = myGraph.findNode(d);
		HashMap<Node, Integer> neighbors = null;
		
		LinkedList<Node> frontier = new LinkedList<Node>(); //list of unvisited nodes
		
		System.out.println("BREADTH FIRST SEARCH:");
		frontier.add(start); //enqueueing start node to frontier
		int cost = 0; //initial cost is 0
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node shallow = frontier.poll(); //dequeue unvisited node
			System.out.println("Visiting "+shallow.getName()+"...");
			shallow.setVisited(true); //marking node as visited
			
			if (shallow.equals(end)){ //goal test
				System.out.println("Found destination: "+shallow.getName());
				System.out.println("Total cost: "+shallow.getCost());
				return shallow;
			}	
			neighbors = shallow.getNeighbors();
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited() && neighbors.get(n) != -1){ //enqueue node if hasn't been visited and there is a path to get there
					System.out.println(n.getName()+" hasn't been visited, adding to frontier...");
					
					if(n.getCost() == 0){ //Verify that this will be the first time that cost is being set (First access according to queue)
						n.setCost(shallow.getCost() + neighbors.get(n)); //setting total cost of getting to node from start node
						cost = neighbors.get(n) + shallow.getCost();
						//System.out.println("COST to  "+ n.getName()+":" + cost);
					}
					//cost+=n.getCost();//cumulative cost so far
					//System.out.println("Cost so far: "+cost);
					frontier.addLast(n);
					System.out.println("Frontier: "+frontier.toString());
				}
			}
			
		}
		System.out.println("Destination not found :(");
		return null;
	}
	
	public Node depthFirst(String sta, String dest, LinkedList<Node> frontier){
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		HashMap<Node, Integer> neighbors = null;
		Node nodeResult = null;
		
		frontier.push(start); //pushing (stack) start node to frontier
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node top = frontier.peek(); //check the top of the stack
			neighbors = top.getNeighbors();
			top.setVisited(true); //marking node as visited to avoid loops
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited() && neighbors.get(n) != -1){ //enqueue node if hasn't been visited and there is a path to get there
					System.out.println(n.getName()+" hasn't been visited, pushing to frontier...");
					nodeResult = depthFirst(n.getName(), dest, frontier);
				}
			}
			try{
				top = frontier.pop();
				if(nodeResult!= null)
					return nodeResult; //This is done to avoid going through nodes missing in stack
				
				System.out.println("Visiting "+top.getName()+"...");
				//top.setVisited(true); 
				
				if (top.equals(end)){ //goal test
					System.out.println("Found destination: "+top.getName());
					//System.out.println("Total cost: "+top.getCost());
					return top;
				}else{
					return null;
				}
			}catch(NoSuchElementException e){
				System.out.println("Stack is empty!");
				return null;
			}
		}
		return null;
	}
	
	public Node uniformCost(String start, String destination){
		return null;
	}
	
	public static void main(String args[]){
		LinkedList<Node> frontier = new LinkedList<Node>(); //stack of unvisited nodes (depthfirst)
		myGraph = new Graph();
		myGraph.load("graph.txt");
		//myGraph.print();
		UninformedSearch us = new UninformedSearch();
		Node breadthResult = us.breadthFirst("Celayork","Leondres");
		
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("DEPTH FIRST SEARCH:");
		Node depthResult = us.depthFirst("Celayork", "Leondres", frontier);
		
		if(depthResult==null)
			System.out.println("Destination not found! :(");
		
	}
}
