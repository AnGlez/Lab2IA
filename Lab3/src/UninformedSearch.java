import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class UninformedSearch {
	
	public static Graph myGraph;

	public Node breadthFirst(int s, int d){
		
		Node start = myGraph.findNode(s);
		Node end = myGraph.findNode(d);
		LinkedList<Node> frontier = new LinkedList<Node>(); //list of unvisited nodes
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];

		System.out.println("BREADTH FIRST SEARCH:");
		frontier.add(start); //enqueueing start node to frontier
		int cost = 0; //initial cost is 0
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node shallow = frontier.poll(); //dequeue unvisited node
			System.out.println("Visiting node "+shallow.getId()+" and removing it from the frontier");
			shallow.setVisited(true); //marking node as visited
			
			if (shallow.equals(end)){ //goal test
				System.out.println("Found destination: "+shallow.getId());
				return shallow;
			}	
			
			for (Node n : shallow.getNeighbors().keySet()){ //check each node's neighbor
				if (!n.isVisited() && !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
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

	public Node depthFirst(int sta, int dest, LinkedList<Node> frontier){
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		HashMap<Node, Integer> neighbors = null;
		Node nodeResult = null;
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];

		frontier.push(start); //pushing (stack) start node to frontier
		System.out.println("Frontier: "+frontier.toString());
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node top = frontier.peek(); //check the top of the stack
			neighbors = top.getNeighbors();
			top.setVisited(true); //marking node as visited to avoid loops
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()&& !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
					System.out.println(n.getId()+" hasn't been visited, pushing to frontier...");
					nodeResult = depthFirst(n.getId(), dest, frontier);
				}
			}
			try{
				top = frontier.pop();
				if(nodeResult!= null)
					return nodeResult; //This is done to avoid going through nodes missing in stack
				
				System.out.println("Visiting "+top.getId()+"...");
				//top.setVisited(true); 
				
				if (top.equals(end)){ //goal test
					System.out.println("Found destination: "+top.getId());
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
		System.out.println("Destination not found :(");
		return null;
	}
	
	public Node uniformCost(int sta, int dest){
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new Comparer()); //This constructor calls the comparer class which defines property to compare 
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		int pathCost = 0;
		HashMap<Node, Integer> neighbors = null;
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];

		frontier.add(start);

		while (!frontier.isEmpty()){ //while there are still nodes to visit
			System.out.println("Frontier: "+frontier.toString());

			Node top = frontier.poll();
			top.setVisited(true); //marking node as visited to avoid loops
			System.out.println("Node " + top.getId()+" cost "+ top.getCost());

			if (top.equals(end)){ //goal test
				System.out.println("Found destination: "+top.getId());
				return top;
			}	
			
			neighbors = top.getNeighbors();
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()&& !inFrontier[n.getId()]){ //There is a path to get to node
						pathCost = neighbors.get(n) + top.getCost();
						
						if(!frontier.contains(n)){ //If I haven't inserted it, then I add it to queue
							System.out.println(n.getId()+" inserted with cost... "+ pathCost);
							n.setCost(pathCost);
							frontier.add(n);
							
						}else if(pathCost < n.getCost()){ //In case a Node was already in frontier, but we find a better path
							System.out.println(n.getId()+" updated with cost... "+ pathCost);
							frontier.remove(n); //In case there is a new way to get to a node cheaper, update expensive path
							n.setCost(pathCost);
							frontier.add(n);

						}

				}
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		LinkedList<Node> frontier = new LinkedList<Node>(); 
		myGraph = new Graph();
		myGraph.load("graph.txt");
		//myGraph.print();
		UninformedSearch us = new UninformedSearch();
		Node end = us.breadthFirst(0,2);
		
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("DEPTH FIRST SEARCH:");
		Node depthResult = us.depthFirst(1, 2, frontier);
		if(depthResult==null)
			System.out.println("Destination not found! :(");
		
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("UNIFORM COST SEARCH:");
		us.uniformCost(1, 2);
		
	}
}


